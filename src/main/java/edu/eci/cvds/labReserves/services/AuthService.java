package edu.eci.cvds.labReserves.services;

import edu.eci.cvds.labReserves.collections.UserMongodb;
import edu.eci.cvds.labReserves.dto.AuthRequest;
import edu.eci.cvds.labReserves.dto.TokenResponse;
import edu.eci.cvds.labReserves.model.LabReserveException;
import edu.eci.cvds.labReserves.model.User;
import edu.eci.cvds.labReserves.repository.mongodb.UserMongoRepository;
import edu.eci.cvds.labReserves.security.CustomUserDetailsService;
import edu.eci.cvds.labReserves.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * AuthService provides authentication and registration functionalities for the system.
 * It handles user registration, login, and JWT token generation.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager; //Manages authentication processes.

    @Autowired
    private CustomUserDetailsService userDetailsService; //Service for loading user details.

    @Autowired
    private UserMongoRepository userRepo; //Repository for interacting with the user database.

    @Autowired
    private PasswordEncoder passwordEncoder; //Handles password encryption.

    @Autowired
    private JwtUtil jwtUtil; //Utility class for generating JWT tokens.

    /**
     * Registers a new user in the system.
     * Encrypts the password before storing it in the database and generates authentication tokens.
     *
     * @param user The user to be registered.
     * @return A TokenResponse containing the JWT token and refresh token.
     * @throws LabReserveException If an error occurs during user registration.
     */
    public TokenResponse register(User user) throws LabReserveException {
        try{
            UserMongodb userMongo = new UserMongodb(user);
            userMongo.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(userMongo);
            String jwtToken = jwtUtil.generateToken(userMongo);
            String refreshToken = jwtUtil.generateRefreshToken(userMongo);
            return  new TokenResponse(jwtToken, refreshToken);

        } catch(Exception e){
            throw new LabReserveException("Error al crear el usuario: " + e.getMessage());
        }
    }

    /**
     * Authenticates a user based on the provided email and password.
     * Generates and returns authentication tokens if authentication is successful.
     *
     * @param authRequest The authentication request containing user credentials.
     * @return A TokenResponse containing the JWT token and refresh token.
     */
    public TokenResponse login(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        );
        UserMongodb userMongodb = userRepo.findByMail(authRequest.getEmail());
        String token = jwtUtil.generateToken(userMongodb);
        String refreshToken = jwtUtil.generateRefreshToken(userMongodb);
        return new TokenResponse(token, refreshToken);
    }
}
