/**
 * Zur Verwendung von Neo4j mit Spring Boot siehe auch:
 * https://www.baeldung.com/spring-data-neo4j-intro
 * <br><br>
 * 
 * Cypher-Befehl, um alle Knoten in der DB zu löschen:
 * <pre>
 * MATCH (n) DETACH DELETE n
 * </pre>
 */
package de.eldecker.spring.personaldb.db;