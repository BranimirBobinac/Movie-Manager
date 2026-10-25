/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dal;

import hr.algebra.dal.sql.ActorRepositorySql;
import hr.algebra.dal.sql.DataSourceSingleton;
import hr.algebra.dal.sql.DirectorRepositorySql;
import hr.algebra.dal.sql.GenreRepositorySql;
import hr.algebra.dal.sql.MovieActorRepositorySql;
import hr.algebra.dal.sql.MovieDirectorRepositorySql;
import hr.algebra.dal.sql.MovieGenreRepositorySql;
import hr.algebra.dal.sql.UserRepositorySql;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dnlbe
 */
public class RepositoryFactory {

    private static Repository repository;
    private static UserRepository userRepository;
    
    private static ActorRepository actorRepository;
    private static DirectorRepository directorRepository;
    private static GenreRepository genreRepository;
    
    private static MovieActorRepository movieActorRepository;
    private static MovieDirectorRepository movieDirectorRepository;
    private static MovieGenreRepository movieGenreRepository;

    private static final String PATH = "/config/repository.properties";

    private static final Properties PROPERTIES = new Properties();
    private static final String CLASS_NAME = "CLASS_NAME";

    static {
        try (InputStream is = RepositoryFactory.class.getResourceAsStream(PATH)) {
            PROPERTIES.load(is);
            repository = (Repository) Class.forName(PROPERTIES.getProperty(CLASS_NAME))
                    .getDeclaredConstructor()
                    .newInstance();
            
            // dodajen ostalih reposa:
            userRepository = new UserRepositorySql();
            actorRepository = new ActorRepositorySql();
            directorRepository = new DirectorRepositorySql();
            genreRepository = new GenreRepositorySql();
            movieActorRepository = new MovieActorRepositorySql();
            movieDirectorRepository = new MovieDirectorRepositorySql();
            movieGenreRepository = new MovieGenreRepositorySql();
            
        } catch (Exception ex) {
            Logger.getLogger(RepositoryFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private RepositoryFactory() {
    }

    // LAZY SINGLETON
    public static Repository getRepository() throws Exception {
        return repository;
    }
    
    public static UserRepository getUserRepository() throws Exception {
        return userRepository;
    }
    
    public static ActorRepository getActorRepository() {
        return actorRepository;
    }

    public static DirectorRepository getDirectorRepository() {
        return directorRepository;
    }

    public static GenreRepository getGenreRepository() {
        return genreRepository;
    }

    public static MovieActorRepository getMovieActorRepository() {
        return movieActorRepository;
    }

    public static MovieDirectorRepository getMovieDirectorRepository() {
        return movieDirectorRepository;
    }

    public static MovieGenreRepository getMovieGenreRepository() {
        return movieGenreRepository;
    }
}
