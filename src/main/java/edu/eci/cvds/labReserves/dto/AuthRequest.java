package edu.eci.cvds.labReserves.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * AuthRequest represents a data transfer object (DTO) for handling authentication requests.
 * It contains user credentials (email and password) required for authentication.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthRequest {

    /**
     * -- GETTER --
     *  Get the email address of the user.
     *
     *
     * -- SETTER --
     *  Set the email address of the user.
     *
     @return The user's email.
      * @param email The new email to set.
     */
    private String email; //The email address of the user.
    /**
     * -- GETTER --
     *  Get the password of the user.
     *
     *
     * -- SETTER --
     *  Set the password of the user.
     *
     @return The user's password.
      * @param password The new password to set.
     */
    private String password; //The password of the user.

}
