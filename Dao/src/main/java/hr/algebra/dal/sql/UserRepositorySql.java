/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.UserRepository;
import hr.algebra.model.Role;
import hr.algebra.model.User;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Optional;
import javax.sql.DataSource;

/**
 *
 * @author Branimir
 */
public class UserRepositorySql implements UserRepository{
    
    private static final String REGISTER_USER = "{ CALL registerUser (?,?,?,?,?) }";
    private static final String LOGIN_USER = "{ CALL loginUser (?) }";
    
    private static final String ID_USER = "IDUser";
    private static final String USERNAME = "Username";
    private static final String USER_PASSWORD = "UserPassword";
    private static final String USER_ROLE = "UserRole";
    private static final String USER_SALT = "Salt";

    @Override
    public int registerUser(User user) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(REGISTER_USER)) {
            stmt.setString(USERNAME, user.getUsername());
            stmt.setString(USER_PASSWORD, user.getPassword());
            stmt.setString(USER_SALT, user.getSalt());
            stmt.setString(USER_ROLE, user.getRole().name());
            stmt.registerOutParameter(ID_USER, Types.INTEGER);
            
            stmt.executeUpdate();
            return stmt.getInt(ID_USER);
        }
    }

    @Override
    public Optional<User> loginUser(String username) throws Exception {
         DataSource dataSource = DataSourceSingleton.getInstance();
    try (Connection con = dataSource.getConnection();
         CallableStatement stmt = con.prepareCall(LOGIN_USER)) {

        stmt.setString(1, username);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Role role = Role.valueOf(rs.getString(USER_ROLE));
                User user = new User(
                        rs.getInt(ID_USER),
                        rs.getString(USERNAME),
                        rs.getString(USER_PASSWORD),
                        role,
                        rs.getString(USER_SALT)
                );

                return Optional.of(user);
            }
        }
    }

    return Optional.empty();
    
    }
}