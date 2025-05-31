package de.eldecker.spring.personaldb.db;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;



@Node("Angestellter")
public class AngestellterNode {

    @Id
    @Property("id")
    private Long _id;

    @Property("vorname")
    private String _vorname;
    
    @Property("nachname")
    private String _nachname;

    @Relationship(type = "IST_UNTERSTELLT", direction = Relationship.Direction.OUTGOING)
    private Set<AngestellterNode> _unterstelltAktuell = new HashSet<>();

    @Relationship(type = "WAR_MAL_UNTERSTELLT", direction = Relationship.Direction.OUTGOING)
    private Set<AngestellterNode> _unterstelltEhemalig = new HashSet<>();

    public AngestellterNode() {}

    public AngestellterNode( String vorname, String nachname ) {
        
        _vorname  = vorname;
        _nachname = nachname;
    }

    public Long getId() { 
        
        return _id; 
    }
    
    public void setId( Long id ) { 
        
        _id = id; 
    }

    public String getVorname() { 
        
        return _vorname; 
    }
    
    public void setVorname( String vorname ) { 
        
        _vorname = vorname; 
    }

    public String getNachname() { 
        
        return _nachname; 
    }
    
    public void setNachname( String nachname ) { 
        
        _nachname = nachname; 
    }

    public Set<AngestellterNode> getAktuelleUnterstellte() { 
        
        return _unterstelltAktuell; 
    }
    
    public void setAktuelleUnterstellte( Set<AngestellterNode> aktuelleUnterstellte ) { 
        
        _unterstelltAktuell = aktuelleUnterstellte; 
    }

    public void addAktuellUnterstellt( AngestellterNode aktuellUnterstellt ) {
        
        _unterstelltAktuell.add( aktuellUnterstellt );
    }
    
    public Set<AngestellterNode> getEhemaligeUnterstellte() { 
        
        return _unterstelltEhemalig; 
    }
    
    public void addEhemaligUnterstellt( AngestellterNode ehemaligUnterstellt ) {
        
        _unterstelltEhemalig.add( ehemaligUnterstellt );
    }
    
    public void setEhemaligeUnterstellte( Set<AngestellterNode> ehemaligeUnterstellte ) { 
        
        _unterstelltEhemalig = ehemaligeUnterstellte; 
    }
    
}
