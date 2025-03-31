package edu.eci.cvds.labReserves.controller;

import edu.eci.cvds.labReserves.dto.AuthRequest;
import edu.eci.cvds.labReserves.dto.TokenResponse;
import edu.eci.cvds.labReserves.model.LabReserveException;
import edu.eci.cvds.labReserves.model.User;
import edu.eci.cvds.labReserves.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication Controller that provides endpoints for user registration and login.
 * This controller handles authentication requests and returns JWT tokens upon successful authentication.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private final AuthService service; //Service that manages authentication logic.

    /**
     * Constructor to inject AuthService dependency.
     *
     * @param service Authentication service instance.
     */
    public AuthController(AuthService service) {
        this.service = service;
    }

    /**
     * Endpoint for user registration.
     *
     * @param user The user details to register.
     * @return JWT tokens upon successful registration.
     * @throws LabReserveException If an error occurs during user creation.
     */
    @PostMapping("/register")
    public ResponseEntity<TokenResponse> registerUser(@RequestBody User user) throws LabReserveException {
        TokenResponse token = service.register(user);
        return ResponseEntity.ok(token);
    }

    /**
     * Endpoint for user login.
     *
     * @param authRequest The authentication request containing user credentials.
     * @return JWT tokens upon successful authentication.
     * @throws Exception If authentication fails.
     */
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> loginUser(@RequestBody AuthRequest authRequest) throws Exception {
        TokenResponse token = service.login(authRequest);
        return ResponseEntity.ok(token);
    }
}
