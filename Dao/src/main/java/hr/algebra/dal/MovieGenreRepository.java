/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.model.Genre;
import java.util.List;

/**
 *
 * @author Branimir
 */
public interface MovieGenreRepository {

    void addGenreToMovie(int movieId, int genreId) throws Exception;
    void clearGenresForMovie(int movieId) throws Exception;
    List<Genre> getGenresForMovie(int movieId) throws Exception;
}
