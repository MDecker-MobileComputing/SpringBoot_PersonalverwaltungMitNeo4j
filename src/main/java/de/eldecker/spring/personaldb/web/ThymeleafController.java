package de.eldecker.spring.personaldb.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.eldecker.spring.personaldb.db.AngestellterNode;
import de.eldecker.spring.personaldb.db.AngestellterNodeRepository;


/**
 * Controller für Thymeleaf-Templates. 
 */
@Controller
@RequestMapping( "/app" )
public class ThymeleafController {
    
    private final static Logger LOG = LoggerFactory.getLogger( ThymeleafController.class );
    
    
    /**
     * Repo-Bean für Zugriff auf Neo4j-Datenbank.
     */
    private AngestellterNodeRepository _angestellterNodeRepository;

    
    /**
     * Konstruktor für Dependency Injection.
     */
    @Autowired
    public ThymeleafController( AngestellterNodeRepository repo ) {
     
        _angestellterNodeRepository = repo;
    }
    
    
    /**
     * Thymeleaf-Methode für Anzeige der Mitarbeiter-Liste (alphabetisch
     * sortiert nach Namen).
     * 
     * @param model Model-Objekt für Platzhalterwerte im Template
     * 
     * @return Immer Template "mitarbeiter-liste.html" zurück.
     */
    @GetMapping( "/liste" )
    public String liste( Model model ) {
                         
        final List<AngestellterNode> liste = _angestellterNodeRepository.findAllByOrderByNachnameAscVornameAsc();
        
        model.addAttribute( "mitarbeiterListe", liste );
        
        return "mitarbeiter-liste";
    }
    
}
