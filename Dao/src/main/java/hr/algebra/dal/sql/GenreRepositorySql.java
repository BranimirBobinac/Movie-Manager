/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.GenreRepository;
import hr.algebra.model.Genre;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

/**
 *
 * @author Branimir
 */
public class GenreRepositorySql implements GenreRepository {

    private static final String CREATE_GENRE = "{ CALL createGenre (?,?) }";
    private static final String UPDATE_GENRE = "{ CALL updateGenre (?, ?) }";
    private static final String DELETE_GENRE = "{ CALL deleteGenre (?) }";
    private static final String SELECT_GENRES = "{ CALL selectGenres }";
    private static final String SELECT_GENRE = "{ CALL selectGenre (?) }";
    private static final String DELETE_ALL_GENRES = "{ CALL deleteAllGenres }";
    
    private static final String ID_GENRE = "IDGenre";
    private static final String NAME = "Name";
    
    @Override
    public void createGenre(Genre genre) throws Exception {
        try (Connection con = DataSourceSingleton.getInstance().getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_GENRE)) {
            stmt.setString(NAME, genre.getName());
            stmt.registerOutParameter(ID_GENRE, Types.INTEGER);
           
            stmt.executeUpdate();
            
            genre.setIdGenre(stmt.getInt(ID_GENRE));
        }
    }

    @Override
    public void updateGenre(int id, Genre genre) throws Exception {
        try (Connection con = DataSourceSingleton.getInstance().getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_GENRE)) {
            stmt.setString(NAME, genre.getName());
           
            stmt.setInt(ID_GENRE, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteGenre(int id) throws Exception {
        try (Connection con = DataSourceSingleton.getInstance().getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_GENRE)) {
            stmt.setInt(ID_GENRE, id);
           
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Genre> selectGenres() throws Exception {
        List<Genre> genres = new ArrayList<>();
        
        try (Connection con = DataSourceSingleton.getInstance().getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_GENRES);
                ResultSet rs = stmt.executeQuery()) {
            
                while (rs.next()) {
                    genres.add(new Genre(
                            rs.getInt("IDGenre"),
                            rs.getString("Name")
                    ));
                }
        }
        
        return genres;
    }
    
    @Override
    public Optional<Genre> selectGenre(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); 
                CallableStatement stmt = con.prepareCall(SELECT_GENRE)) {
         
            stmt.setInt(ID_GENRE, id);

            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Genre(
                            rs.getInt(ID_GENRE), 
                            rs.getString(NAME)
                    ));
                }
            }
        
        }    
        return Optional.empty();
    }
    
    @Override 
    public void deleteAllGenres() throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_ALL_GENRES)) {
            stmt.executeUpdate();
        }
    }
    
}
