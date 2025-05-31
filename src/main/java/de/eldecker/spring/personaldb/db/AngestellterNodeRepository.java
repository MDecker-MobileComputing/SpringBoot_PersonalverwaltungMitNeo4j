package de.eldecker.spring.personaldb.db;

import org.springframework.data.neo4j.repository.Neo4jRepository;


public interface AngestellterNodeRepository 
                 extends Neo4jRepository<AngestellterNode, Long> {

}
