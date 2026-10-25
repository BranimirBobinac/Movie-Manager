/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

/**
 *
 * @author Branimir
 */
public class User {
    private int idUser;
    private String username;
    private String password;
    private String salt;
    private Role role;
    
    
    
    public User() {
    }
    
    public User(int idUser, String username, String password, Role role, String salt) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.role = role;
        this.salt = salt;
    }
    
    public User(String username, String password, Role role, String salt) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.salt = salt;
    }
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this.idUser;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        return this.idUser == other.idUser;
    }

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", role=" + role + '}';
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    
}
