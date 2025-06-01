package de.eldecker.spring.personaldb.web;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import de.eldecker.spring.personaldb.db.AngestelltePersonNode;
import de.eldecker.spring.personaldb.db.AngestelltenRepo;


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
    private AngestelltenRepo _angestellterNodeRepository;

    
    /**
     * Konstruktor für Dependency Injection.
     */
    @Autowired
    public ThymeleafController( AngestelltenRepo repo ) {
     
        _angestellterNodeRepository = repo;
    }
    
    
    /**
     * Controller-Methode für Anzeige der Mitarbeiter-Liste (alphabetisch
     * sortiert nach Namen).
     * 
     * @param model Model-Objekt für Platzhalterwerte in Template
     * 
     * @return Immer Template "mitarbeiter-liste.html" zurück
     */
    @GetMapping( "/liste" )
    public String liste( Model model ) {
                         
        final List<AngestelltePersonNode> liste = 
                _angestellterNodeRepository.findAllByOrderByNachnameAscVornameAsc();
        
        model.addAttribute( "personenListe", liste );
        
        return "personen-liste";
    }
    
    
    /**
     * Controller-Methode für Anzeige Details einzelnen Mitarbeiter.
     *  
     * @param id ID der angestellten Person
     * 
     * @param model Model-Objekt für Platzhalterwerte in Template
     * 
     * @return Template "person" oder "fehler"
     */
    @GetMapping( "/person/{id}" )
    public String person( @PathVariable Long id, Model model ) {
        
        final Optional<AngestelltePersonNode> personOptional = _angestellterNodeRepository.findById( id );
        
        if ( personOptional.isEmpty() ) {

            final String fehlerText = String.format( "Keine Person mit ID=%d in Datenbank gefunden.", id );            
            LOG.warn( fehlerText );            
            model.addAttribute( "nachricht", fehlerText );
            
            return "fehler";
        }
        
        LOG.warn( "Details abgerufen fuer: {}", personOptional.get() ); 
        
        model.addAttribute( "person", personOptional.get() );

        return "person-details";
    }

    
}
