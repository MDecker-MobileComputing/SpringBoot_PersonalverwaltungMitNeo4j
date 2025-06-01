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
     * Findet alle direkten und indirekten Vorgesetzten für eine gegebene Person anhand ihrer ID.
     * Die Methode durchläuft die Hierarchie der "IST_UNTERSTELLT"-Beziehungen.
     *
     * @param id Die ID der {@code AngestelltePersonNode}, für die die Vorgesetzten gefunden werden sollen.
     * 
     * @return Eine Liste aller direkten und indirekten {@code AngestelltePersonNode}-Objekte,
     *         die Vorgesetzte der Person mit der gegebenen ID sind. Gibt eine leere Liste zurück,
     *         wenn keine Vorgesetzten gefunden werden oder die Person nicht existiert.
     */
    @Query("""
            MATCH path = (person:AngestelltePerson)-[:IST_UNTERSTELLT*]->(supervisor:AngestelltePerson)
            WHERE person.id = $id
            RETURN nodes(path) AS supervisorChain
            ORDER BY length(path) DESC
            LIMIT 1
        """)
    List<AngestelltePersonNode> getVorgesetztenKette( Long id );

}
