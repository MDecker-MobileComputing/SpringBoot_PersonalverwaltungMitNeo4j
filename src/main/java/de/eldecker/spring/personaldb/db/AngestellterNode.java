package de.eldecker.spring.personaldb.db;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;



@Node("Angestellter")
public class AngestellterNode {

    @Id @GeneratedValue
    private Long id;

    private String vorname;
    
    private String nachname;

    @Relationship(type = "IST_UNTERSTELLT", direction = Relationship.Direction.OUTGOING)
    private AngestellterNode vorgesetzter = null;

    /*
    @Relationship(type = "WAR_MAL_UNTERSTELLT", direction = Relationship.Direction.OUTGOING)
    private Set<AngestellterNode> unterstelltEhemalig = new HashSet<>();
    */

    public AngestellterNode() {}

    public AngestellterNode( String vorname, String nachname ) {
        
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
    
    
    public void setVorgesetzter( AngestellterNode vorgesetzter ) {
        
        this.vorgesetzter = vorgesetzter;
    }
    

    public AngestellterNode getVorgesetzter() {
        
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
        
        sb.append( vorname  ).append( ' ' ).append( nachname ).append( ": " );
        
        if ( vorgesetzter != null ) {
            
            final String vorgesetzterName = vorgesetzter.getVollerName();

            sb.append( "Direkter Vorgesetzter ist " ).append( vorgesetzterName );
            
        } else {
            
            sb.append( "Niemanden unterstellt" );
        }
        
        return sb.toString();
    }
}
