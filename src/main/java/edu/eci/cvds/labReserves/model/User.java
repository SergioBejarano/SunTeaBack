package edu.eci.cvds.labReserves.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

/**
 * Represents a user in LabReserves system.
 * A user has an ID, name, email, password, and a role.
 * The allowed roles are "teacher" and "admin".
 */
@NoArgsConstructor
@Getter
@Setter
public class User {

    private static Set<String> rolType = Set.of("teacher", "admin"); // Set of valid role types for a user.
    /**
     * -- GETTER --
     *  get the id of a user
     *
     * @return the id of a user
     */
    private int id; // Unique identifier for the user
    /**
     * -- GETTER --
     *  Get the full name of the user.
     *
     * @return The user's name.
     */
    private String name; // Full name of the user
    /**
     * -- GETTER --
     *  Get the email address of the user.
     *
     * @return The user's email.
     */
    private String mail; // Email address of the user
    /**
     * -- GETTER --
     *  Get the password of the user.
     *
     * @return The user's password.
     */
    private String password; // Password associated with the user account
    /**
     * -- GETTER --
     *  get the rol of a user
     *
     * @return the rol of a user
     */
    private String rol; // Role assigned to the user

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
