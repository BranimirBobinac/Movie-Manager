/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.model.Actor;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Branimir
 */
public interface ActorRepository {
    void createActor(Actor actor) throws Exception;
    void updateActor(int id, Actor actor) throws Exception;
    void deleteActor(int id) throws Exception;
    void deleteAllActors() throws Exception;
    Optional<Actor> selectActor(int id) throws Exception;
    List<Actor> selectActors() throws Exception;
}
