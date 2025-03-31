package edu.eci.cvds.labReserves.model;

import java.util.*;

/**
 * Represents a user in LabReserves system.
 * A user has an ID, name, email, password, and a role.
 * The allowed roles are "teacher" and "admin".
 */

public class User {

    private static Set<String> rolType = Set.of("teacher", "admin"); // Set of valid role types for a user.
    private int id; // Unique identifier for the user
    private String name; // Full name of the user
    private String mail; // Email address of the user
    private String password; // Password associated with the user account
    private String rol; // Role assigned to the user

    /**
     * Default constructor.
     */
    public User() {}

    /**
     * Constructs a new User with the given attributes.
     *
     * @param id        Unique identifier for the user.
     * @param name      Full name of the user.
     * @param mail      Email address of the user.
     * @param password  Password associated with the user account.
     * @param rol       Role assigned to the user (must be "teacher" or "admin").
     * @throws LabReserveException If the provided role is not valid.
     */
    public User(int id, String name, String mail, String password, String rol) throws  LabReserveException {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.password = password;
        if (rolType.contains(rol)){
            this.rol = rol;
        }else{
            throw new LabReserveException(LabReserveException.INVALID_ROL_TYPE);
        }
    }

    /**
     * get the id of a user
     * @return the id of a user
     */
    public int getId() {
        return id;
    }


    /**
     * set the id of a user
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the full name of the user.
     *
     * @return The user's name.
     */
    public String getName(){
        return name;
    }


    /**
     * Set the full name of the user.
     *
     * @param name The new name to be assigned to the user.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Get the email address of the user.
     *
     * @return The user's email.
     */
    public String getMail(){
        return mail;
    }

    /**
     * Set the email address of the user.
     *
     * @param mail The new email address to be assigned to the user.
     */
    public void setMail(String mail){
        this.mail = mail;
    }

    /**
     * Get the password of the user.
     *
     * @return The user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password of the user.
     *
     * @param password The new password to be assigned to the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * get the rol of a user
     * @return the rol of a user
     */
    public String getRol(){return rol;}

    /**
     * set a new rol for a user
     * @param newRol
     */
    public void setRol(String newRol) throws LabReserveException {
        if (rolType.contains(newRol)){
            this.rol = newRol;
        }else{
            throw new LabReserveException(LabReserveException.INVALID_ROL_TYPE);
        }
    }
}