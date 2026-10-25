/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.model.Director;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Branimir
 */
public interface DirectorRepository {
    void createDirector(Director director) throws Exception;
    void updateDirector(int id, Director director) throws Exception;
    void deleteDirector(int id) throws Exception;
    void deleteAllDirectors() throws Exception;
    Optional<Director> selectDirector(int id) throws Exception;
    List<Director> selectDirectors() throws Exception;
}
