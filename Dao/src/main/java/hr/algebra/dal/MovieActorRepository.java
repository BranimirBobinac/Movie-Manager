/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.model.Actor;
import java.util.List;

/**
 *
 * @author Branimir
 */
public interface MovieActorRepository {
    void addActorToMovie(int movieId, int actorId) throws Exception;
    void clearActorsMovie(int movieId) throws Exception;
    List<Actor> getActorsForMovie(int movieId) throws Exception;
}
