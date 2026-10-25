/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.MovieActorRepository;
import hr.algebra.model.Actor;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Branimir
 */
public class MovieActorRepositorySql implements MovieActorRepository {
    
    private static final String ID_MOVIE = "IDMovie";
    private static final String ID_ACTOR = "IDActor";
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    
    private static final String ADD_ACTOR_TO_MOVIE = "{ CALL addActorToMovie (?,?) }";
    private static final String CLEAR_ACTORS_FOR_MOVIE = "{ CALL clearActorsForMovie (?) }";
    private static final String GET_ACTORS_FOR_MOVIE = "{ CALL getActorsForMovie (?) }";

    @Override
    public void addActorToMovie(int movieId, int actorId) throws Exception {
        try (Connection con = DataSourceSingleton.getInstance().getConnection();
                CallableStatement stmt = con.prepareCall(ADD_ACTOR_TO_MOVIE)) {
            stmt.setInt(ID_MOVIE, movieId);
            stmt.setInt(ID_ACTOR, actorId);
            stmt.executeUpdate();
        }
    }

    @Override
    public void clearActorsMovie(int movieId) throws Exception {
        try (Connection con = DataSourceSingleton.getInstance().getConnection();
                CallableStatement stmt = con.prepareCall(CLEAR_ACTORS_FOR_MOVIE)) {
            
            stmt.setInt(ID_MOVIE, movieId);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Actor> getActorsForMovie(int movieId) throws Exception {
        List<Actor> actors = new ArrayList<>();

        try (Connection con = DataSourceSingleton.getInstance().getConnection();
             CallableStatement stmt = con.prepareCall(GET_ACTORS_FOR_MOVIE)) {

            stmt.setInt(ID_MOVIE, movieId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    actors.add(new Actor(
                        rs.getInt(ID_ACTOR),
                        rs.getString(FIRST_NAME),
                        rs.getString(LAST_NAME)
                    ));
                }
            }
        }

        return actors;
    }
}
