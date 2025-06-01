package de.eldecker.spring.personaldb.logik;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import de.eldecker.spring.personaldb.db.AngestellterNode;
import de.eldecker.spring.personaldb.db.AngestellterNodeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Mit der Bean dieser Klasse werden unmittelbar nach dem Hochfahren der Anwendung
 * Demo-Daten importiert (aber nur, wenn die Datenbank noch ganz leer ist).
 */
@Component
public class DemoDatenImporter implements ApplicationRunner {

    private final static Logger LOG = LoggerFactory.getLogger( DemoDatenImporter.class );
    
    private AngestellterNodeRepository _angestellterRepo;
    
    
    /**
     * Konstruktor für <i>Dependency Injection</i>
     */
    public DemoDatenImporter( AngestellterNodeRepository angestellterRepo ) {
        
        _angestellterRepo = angestellterRepo;
    }
    
    
    /**
     * Implementierung einziger Methode aus Interface {@code ApplicationRunner}.
     * 
     * @param args Wird nicht ausgewertet
     */
    public void run( ApplicationArguments args ) throws Exception {
    
        final long anzahlAngestellteVorher = _angestellterRepo.count();
        LOG.info( "Anzahl Angestellte in Datenbank: {}", anzahlAngestellteVorher );
        
        if ( anzahlAngestellteVorher > 0) {
         
            LOG.info( "Datenbank ist nicht leer, importiere keine Demo-Daten." );
            return;
        }
           
        LOG.info( "Datenbank ist leer, importiere Demo-Daten..." );

        
        AngestellterNode gruender = erzeugeAngestellter( "Gerda", "Gründer", null, null );
        
        AngestellterNode manager1 = erzeugeAngestellter( "Manfred", "Meier", gruender, null );
        AngestellterNode manager2 = erzeugeAngestellter( "Melanie", "Meyer", gruender, null );
                        
        AngestellterNode ma1 = erzeugeAngestellter( "Alred" , "Armbruster", manager1, null );
        AngestellterNode ma2 = erzeugeAngestellter( "Bob"   , "Brecht"    , manager1, null );
        AngestellterNode ma3 = erzeugeAngestellter( "Claire", "Cramer"    , manager1, null );
        
        final long anzahlAngestellteNachher = _angestellterRepo.count();
        LOG.info( "Anzahl Angestellte in Datenbank nach Import Demo-Daten: {}", 
                  anzahlAngestellteNachher );
        
        final long anzahlIstUnterstelltBeziehungen = _angestellterRepo.zaehleIstUnterstelltRelationships();
        LOG.info( "Anzahl Beziehungen >ist unterstellt< in Datenbank nach Import Demo-Daten: {}", 
                  anzahlIstUnterstelltBeziehungen );        
    }
    
    
    /**
     * Neuen Angestellten erzeugen und in Datenbank speichern.
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
    private AngestellterNode erzeugeAngestellter( String vorname, String nachname, 
                                                  AngestellterNode vorgesetzterAktuell,
                                                  AngestellterNode vorgesetzterEhemals ) {
        
        AngestellterNode angesteller = new AngestellterNode( vorname, nachname );
        
        if ( vorgesetzterAktuell != null ) {
            
            angesteller.setVorgesetzter( vorgesetzterAktuell );
        }
        
        /*
        if ( vorgesetzterEhemals != null ) {
            
            angesteller.addEhemaligUnterstellt( vorgesetzterEhemals );
        }
        */
        
        return _angestellterRepo.save( angesteller );
    }

}
