package edu.eci.cvds.labReserves.dto;

/**
 * AuthRequest represents a data transfer object (DTO) for handling authentication requests.
 * It contains user credentials (email and password) required for authentication.
 */
public class AuthRequest {

    private String email; //The email address of the user.
    private String password; //The password of the user.

    /**
     * Default constructor.
     */
    public AuthRequest() {
    }

    /**
     * Constructs an AuthRequest with the given email and password.
     *
     * @param email The email address of the user.
     * @param password The password of the user.
     */
    public AuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Get the email address of the user.
     *
     * @return The user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email address of the user.
     *
     * @param email The new email to set.
     */
    public void setEmail(String email) {
        this.email = email;
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
     * @param password The new password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
