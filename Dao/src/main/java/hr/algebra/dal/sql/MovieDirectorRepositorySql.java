/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.MovieDirectorRepository;
import hr.algebra.model.Director;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Branimir
 */
public class MovieDirectorRepositorySql implements MovieDirectorRepository {

    private static final String ID_MOVIE = "IDMovie";
    private static final String ID_DIRECTOR = "IDDirector";
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";

    private static final String ADD_DIRECTOR_TO_MOVIE = "{ CALL addDirectorToMovie (?, ?) }";
    private static final String CLEAR_DIRECTORS_FOR_MOVIE = "{ CALL clearDirectorsForMovie (?) }";
    private static final String GET_DIRECTORS_FOR_MOVIE = "{ CALL getDirectorsForMovie (?) }";

    @Override
    public void addDirectorToMovie(int movieId, int directorId) throws Exception {
        try (Connection con = DataSourceSingleton.getInstance().getConnection(); CallableStatement stmt = con.prepareCall(ADD_DIRECTOR_TO_MOVIE)) {

            stmt.setInt(ID_MOVIE, movieId);
            stmt.setInt(ID_DIRECTOR, directorId);
            stmt.executeUpdate();
        }
    }

    @Override
    public void clearDirectorsForMovie(int movieId) throws Exception {
        try (Connection con = DataSourceSingleton.getInstance().getConnection(); CallableStatement stmt = con.prepareCall(CLEAR_DIRECTORS_FOR_MOVIE)) {

            stmt.setInt(ID_MOVIE, movieId);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Director> getDirectorsForMovie(int movieId) throws Exception {
        List<Director> directors = new ArrayList<>();

        try (Connection con = DataSourceSingleton.getInstance().getConnection(); CallableStatement stmt = con.prepareCall(GET_DIRECTORS_FOR_MOVIE)) {

            stmt.setInt(ID_MOVIE, movieId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    directors.add(new Director(
                            rs.getInt(ID_DIRECTOR),
                            rs.getString(FIRST_NAME),
                            rs.getString(LAST_NAME)
                    ));
                }
            }
        }

        return directors;
    }
}
