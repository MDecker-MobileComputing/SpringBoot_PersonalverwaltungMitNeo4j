package de.eldecker.spring.personaldb.logik;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import de.eldecker.spring.personaldb.db.AngestelltePersonNode;
import de.eldecker.spring.personaldb.db.AngestelltenRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Mit der Bean dieser Klasse werden unmittelbar nach dem Hochfahren der Anwendung
 * Demo-Daten importiert (aber nur, wenn die Datenbank noch ganz leer ist).
 */
@Component
public class DemoDatenImporter implements ApplicationRunner {

    private final static Logger LOG = LoggerFactory.getLogger( DemoDatenImporter.class );
    
    /** Bean für Zugriff auf Knoten=Datensätzen mit angestellten Personen. */
    private AngestelltenRepo _angestellterRepo;
    
    
    /**
     * Konstruktor für <i>Dependency Injection</i>
     */
    public DemoDatenImporter( AngestelltenRepo angestellterRepo ) {
        
        _angestellterRepo = angestellterRepo;
    }
    
    
    /**
     * Implementierung einziger Methode aus Interface {@code ApplicationRunner}.
     * 
     * @param args Wird nicht ausgewertet
     */
    public void run( ApplicationArguments args ) throws Exception {
    
        final long anzahlAngestellteVorher = _angestellterRepo.count();
        
        if ( anzahlAngestellteVorher > 0) {
         
            LOG.info( "Datenbank enthält schon {} Personen, es werden keine Demo-Daten importiert.", 
                      anzahlAngestellteVorher );
            return;
        }
           
        LOG.info( "Datenbank ist leer, importiere Demo-Daten..." );

        
        AngestelltePersonNode gruender = erzeugeAngestellter( "Gerda", "Gründer", null, null );
        
        AngestelltePersonNode manager1 = erzeugeAngestellter( "Manfred", "Meier", gruender, null );
        AngestelltePersonNode manager2 = erzeugeAngestellter( "Melanie", "Meyer", gruender, null );
                        
        erzeugeAngestellter( "Alfred" , "Armbruster", manager1, null );
        erzeugeAngestellter( "Bob"    , "Brecht"    , manager1, null );
        erzeugeAngestellter( "Claire" , "Cramer"    , manager1, null );        
        erzeugeAngestellter( "Dave"   , "Dolllinger", manager2, null );
        
        AngestelltePersonNode edwin = erzeugeAngestellter( "Edwin", "Eger", manager2, null );
        
        erzeugeAngestellter( "Peter", "Praktikant", edwin, null );
                
        
        final long anzahlAngestellteNachher = _angestellterRepo.count();
        LOG.info( "Anzahl Angestellte in Datenbank nach Import Demo-Daten: {}", 
                  anzahlAngestellteNachher );
        
        final long anzahlIstUnterstelltBeziehungen = _angestellterRepo.zaehleIstUnterstelltRelationships();
        LOG.info( "Anzahl Beziehungen >ist unterstellt< in Datenbank nach Import Demo-Daten: {}", 
                  anzahlIstUnterstelltBeziehungen );        
    }
    
    
    /**
     * Neue angestellte Person erzeugen und in Datenbank speichern.
     * 
     * @param vorname Vorname Angestellter
     * 
     * @param nachname Nachname Angesteller
     * 
     * @param vorgesetzterAktuell Vorgesetzter, kann {@code null} sein
     * 
     * @param vorgesetzterEhemals Ehemaliger Vorgesetzter, kann {@code null} sein
     * 
     * @return Neu erzeugtes Objekt
     */
    private AngestelltePersonNode erzeugeAngestellter( String vorname, String nachname, 
                                                       AngestelltePersonNode vorgesetzterAktuell,
                                                       AngestelltePersonNode vorgesetzterEhemals ) {
        
        AngestelltePersonNode angesteller = new AngestelltePersonNode( vorname, nachname );
        
        if ( vorgesetzterAktuell != null ) {
            
            angesteller.setVorgesetzter( vorgesetzterAktuell );
        }
        
        return _angestellterRepo.save( angesteller );
    }

}
