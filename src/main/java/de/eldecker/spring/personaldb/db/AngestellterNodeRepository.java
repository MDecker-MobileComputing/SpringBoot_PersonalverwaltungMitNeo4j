package de.eldecker.spring.personaldb.db;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;


public interface AngestellterNodeRepository 
                 extends Neo4jRepository<AngestellterNode, Long> {

    @Query("MATCH ()-[r:IST_UNTERSTELLT]->() RETURN count(r)")
    long zaehleIstUnterstelltRelationships();
}
