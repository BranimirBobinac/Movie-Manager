/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.MovieGenreRepository;
import hr.algebra.model.Genre;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Branimir
 */
public class MovieGenreRepositorySql implements MovieGenreRepository {

    private static final String ID_MOVIE = "IDMovie";
    private static final String ID_GENRE = "IDGenre";
    private static final String NAME = "Name";

    private static final String ADD_GENRE_TO_MOVIE = "{ CALL addGenreToMovie (?, ?) }";
    private static final String CLEAR_GENRES_FOR_MOVIE = "{ CALL clearGenresForMovie (?) }";
    private static final String GET_GENRES_FOR_MOVIE = "{ CALL getGenresForMovie (?) }";

    @Override
    public void addGenreToMovie(int movieId, int genreId) throws Exception {
        try (Connection con = DataSourceSingleton.getInstance().getConnection(); CallableStatement stmt = con.prepareCall(ADD_GENRE_TO_MOVIE)) {

            stmt.setInt(ID_MOVIE, movieId);
            stmt.setInt(ID_GENRE, genreId);
            stmt.executeUpdate();
        }
    }

    @Override
    public void clearGenresForMovie(int movieId) throws Exception {
        try (Connection con = DataSourceSingleton.getInstance().getConnection(); CallableStatement stmt = con.prepareCall(CLEAR_GENRES_FOR_MOVIE)) {

            stmt.setInt(ID_MOVIE, movieId);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Genre> getGenresForMovie(int movieId) throws Exception {
        List<Genre> genres = new ArrayList<>();

        try (Connection con = DataSourceSingleton.getInstance().getConnection(); CallableStatement stmt = con.prepareCall(GET_GENRES_FOR_MOVIE)) {

            stmt.setInt(ID_MOVIE, movieId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    genres.add(new Genre(
                            rs.getInt(ID_GENRE),
                            rs.getString(NAME)
                    ));
                }
            }
        }

        return genres;
    }
}
