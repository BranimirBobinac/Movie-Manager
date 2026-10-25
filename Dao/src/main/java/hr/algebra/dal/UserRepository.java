/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.model.User;
import java.util.Optional;

/**
 *
 * @author Branimir
 */
public interface UserRepository {
    int registerUser(User user) throws Exception;
    Optional<User> loginUser(String username) throws Exception;
}
