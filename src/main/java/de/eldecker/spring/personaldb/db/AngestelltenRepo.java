package de.eldecker.spring.personaldb.db;

import java.util.List;


import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;


/**
 * Repository-Interface für AngestellterNode, das die Interaktion mit der Neo4j-Datenbank
 * ermöglicht. Es erweitert Neo4jRepository, um CRUD-Operationen und benutzerdefinierte
 * Abfragen zu unterstützen.
 */
public interface AngestelltenRepo 
                 extends Neo4jRepository<AngestelltePersonNode, Long> {

    /**
     * Methode mit Cypher-Query um die Anzahl der "IST_UNTERSTELLT"-Relationships
     * zu zählen. Diese Relationship verbinden Angestellte mit ihren Vorgesetzten.
     * 
     * @return Anzahl der "IST_UNTERSTELLT"-Relationships
     */
    @Query( "MATCH ()-[r:IST_UNTERSTELLT]->() RETURN count(r)" )
    long zaehleIstUnterstelltRelationships();


    /**
     * <i>Derived Query Method</i> um alle angestellte Personen sortiert nach 
     * Nachname und Vorname in aufsteigender Reihenfolge zu finden.
     * 
     * @return Sortierte Liste von Angstellten
     */
    List<AngestelltePersonNode> findAllByOrderByNachnameAscVornameAsc();


    /**
     * Methode mit Cypher-Query, um alle Vorgesetzten einer angestellten Person
     * bis zum Wurzelknoten (Root) zu finden.
     * 
     * @param id ID der angestellten Person, deren Vorgesetzte gesucht werden
     * 
     * @return Liste der "Vorgesetztenkette"
     */
    @Query("""
        MATCH path = (p:AngestelltePerson)-[:IST_UNTERSTELLT*1..]->(v:AngestelltePerson)
        WHERE ID(p) = $id AND NOT (v)-[:IST_UNTERSTELLT]->()
        RETURN nodes(path) AS nodes
    """)
    List<AngestelltePersonNode> findSuperiorsUpToRoot( @Param("id") Long id );

}
