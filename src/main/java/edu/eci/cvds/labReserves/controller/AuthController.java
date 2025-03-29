package edu.eci.cvds.labReserves.controller;

import edu.eci.cvds.labReserves.collections.UserMongodb;
import edu.eci.cvds.labReserves.dto.AuthRequest;
import edu.eci.cvds.labReserves.dto.TokenResponse;
import edu.eci.cvds.labReserves.model.LabReserveException;
import edu.eci.cvds.labReserves.model.User;
import edu.eci.cvds.labReserves.repository.mongodb.UserMongoRepository;
import edu.eci.cvds.labReserves.dto.AuthRequest;
import edu.eci.cvds.labReserves.collections.UserMongodb;
import edu.eci.cvds.labReserves.security.CustomUserDetailsService;
import edu.eci.cvds.labReserves.services.AuthService;
import edu.eci.cvds.labReserves.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> registerUser(@RequestBody User user) throws LabReserveException {
        TokenResponse token = service.register(user);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> loginUser(@RequestBody AuthRequest authRequest) throws Exception {
        TokenResponse token = service.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
}
