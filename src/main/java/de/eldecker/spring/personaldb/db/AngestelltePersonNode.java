package de.eldecker.spring.personaldb.db;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;


/**
 * Klasse für Knoten (Node), um eine in der Firma angestellte Person zu repräsentieren.
 */
@Node("AngestelltePerson")
public class AngestelltePersonNode {

    /** Primärschlüssel */
    @Id @GeneratedValue
    private Long id;

    /** Vorname der angestellten Person. */
    private String vorname;
    
    /** Nachname (Familienname) der angestellten Person. */
    private String nachname;

    /** Referenz zum direkten Vorgesetzten. */
    @Relationship( type = "IST_UNTERSTELLT", direction = Relationship.Direction.OUTGOING )
    private AngestelltePersonNode vorgesetzter = null;

    /*
    @Relationship(type = "WAR_MAL_UNTERSTELLT", direction = Relationship.Direction.OUTGOING)
    private Set<AngestellterNode> unterstelltEhemalig = new HashSet<>();
    */

    /**
     * Default-Konstruktor.
     */
    public AngestelltePersonNode() {}

    
    /**
     * Konstruktor, um Vor- und Nachname zu setzen.
     * 
     * @param vorname Vorname der angestellten Person
     * 
     * @param nachname Familienname der angestellten Person
     */
    public AngestelltePersonNode( String vorname, String nachname ) {
        
        this.vorname  = vorname;
        this.nachname = nachname;
    }

    public Long getId() { 
        
        return id; 
    }
    
    public void setId( Long id ) { 
        
        this.id = id; 
    }

    public String getVorname() { 
        
        return this.vorname; 
    }
    
    public void setVorname( String vorname ) { 
        
        this.vorname = vorname; 
    }

    public String getNachname() { 
        
        return this.nachname; 
    }
    
    public void setNachname( String nachname ) { 
        
        this.nachname = nachname; 
    }
    
    
    public void setVorgesetzter( AngestelltePersonNode vorgesetzter ) {
        
        this.vorgesetzter = vorgesetzter;
    }
    

    public AngestelltePersonNode getVorgesetzter() {
        
        return this.vorgesetzter;
    }


    /**
     * Gibt den vollen Namen des Angestellten zurück, bestehend aus Vorname und Nachname.
     * 
     * @return Voller Name des Angestellten
     */
    public String getVollerName() {
        
        return vorname + " " + nachname;
    }

    
    /**
     * Gibt vollen Namen des Angestellten zurück inkl. Vorgesetzter (falls vorhanden).
     * 
     * @return String-Darstellung des Angestellten
     */
    @Override
    public String toString() {
        
        StringBuffer sb = new StringBuffer();
        
        sb.append( vorname  ).append( ' ' )
          .append( nachname )
          .append( " hat als direkten Vorgesetzten " );
        
        if ( vorgesetzter != null ) {
            
            sb.append( vorgesetzter.getVollerName() );
            
        } else {
            
            sb.append( "niemanden." );
        }
        
        return sb.toString();
    }

}
