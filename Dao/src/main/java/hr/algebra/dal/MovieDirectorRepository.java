/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.model.Director;
import java.util.List;

/**
 *
 * @author Branimir
 */
public interface MovieDirectorRepository {
    void addDirectorToMovie(int movieId, int directorId) throws Exception;
    void clearDirectorsForMovie(int movieId) throws Exception;
    List<Director> getDirectorsForMovie(int movieId) throws Exception;
}
