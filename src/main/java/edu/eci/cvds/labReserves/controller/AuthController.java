package edu.eci.cvds.labReserves.controller;

import edu.eci.cvds.labReserves.dto.AuthRequest;
import edu.eci.cvds.labReserves.dto.TokenResponse;
import edu.eci.cvds.labReserves.model.LabReserveException;
import edu.eci.cvds.labReserves.model.User;
import edu.eci.cvds.labReserves.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Authentication Controller that provides endpoints
 * for user registration and login.
 * This controller handles authentication requests
 * and returns JWT tokens upon successful authentication.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    /** Service that manages authentication logic. */
    private final AuthService service;

    /**
     * Constructor to inject AuthService dependency.
     *
     * @param authService Authentication service instance.
     */
    public AuthController(final AuthService authService) {
        this.service = authService;
    }

    /**
     * Endpoint for user registration.
     *
     * @param user The user details to register.
     * @return JWT tokens upon successful registration.
     * @throws LabReserveException If an error occurs during user creation.
     */
    @PostMapping("/register")
    public ResponseEntity<TokenResponse> registerUser(
            @RequestBody final User user) throws LabReserveException {
        final TokenResponse token = service.register(user);
        return ResponseEntity.ok(token);
    }

    /**
     * Endpoint for user login.
     *
     * @param authRequest The authentication request containing credentials.
     * @return JWT tokens upon successful authentication.
     */
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> loginUser(
            @RequestBody final AuthRequest authRequest) {
        final TokenResponse token = service.login(authRequest);
        return ResponseEntity.ok(token);
    }
}
