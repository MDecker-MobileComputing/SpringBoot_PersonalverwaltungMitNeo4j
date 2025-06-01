package de.eldecker.spring.personaldb.db;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;


/**
 * Repository-Interface für AngestellterNode, das die Interaktion mit der Neo4j-Datenbank
 * ermöglicht. Es erweitert Neo4jRepository, um CRUD-Operationen und benutzerdefinierte
 * Abfragen zu unterstützen.
 */
public interface AngestellterNodeRepository 
                 extends Neo4jRepository<AngestellterNode, Long> {

    /**
     * Methode mit Cypher-Query um die Anzahl der "IST_UNTERSTELLT"-Relationships
     * zu zählen. Diese Relationship verbinden Angestellte mit ihren Vorgesetzten.
     * 
     * @return Anzahl der "IST_UNTERSTELLT"-Relationships
     */
    @Query( "MATCH ()-[r:IST_UNTERSTELLT]->() RETURN count(r)" )
    long zaehleIstUnterstelltRelationships();


    /**
     * Derived Query Method um alle Angestellten sortiert nach Nachname und Vorname
     * in aufsteigender Reihenfolge zu finden.
     * 
     * @return Sortierte Liste von Angstellten
     */
    List<AngestellterNode> findAllByOrderByNachnameAscVornameAsc();

}
 