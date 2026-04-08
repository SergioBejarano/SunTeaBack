package edu.eci.cvds.labReserves.services;

import edu.eci.cvds.labReserves.collections.UserMongodb;
import edu.eci.cvds.labReserves.dto.AuthRequest;
import edu.eci.cvds.labReserves.dto.TokenResponse;
import edu.eci.cvds.labReserves.model.LabReserveException;
import edu.eci.cvds.labReserves.model.User;
import edu.eci.cvds.labReserves.repository.mongodb.UserMongoRepository;
import edu.eci.cvds.labReserves.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service providing authentication and registration functionalities.
 * Handles user registration, login, and JWT token generation.
 */
@Service
public class AuthService {

    /** Manages authentication processes. */
    private final AuthenticationManager authenticationManager;

    /** Repository for interacting with the user database. */
    private final UserMongoRepository userRepo;

    /** Handles password encryption. */
    private final PasswordEncoder passwordEncoder;

    /** Utility class for generating JWT tokens. */
    private final JwtUtil jwtUtil;

    /**
     * Constructs the AuthService with necessary dependencies.
     *
     * @param pAuthManager Manager for authentication.
     * @param pUserRepo    Repository for user data.
     * @param pPassEncoder Encoder for passwords.
     * @param pJwtUtil     Utility for JWT tokens.
     */
    public AuthService(final AuthenticationManager pAuthManager,
                       final UserMongoRepository pUserRepo,
                       final PasswordEncoder pPassEncoder,
                       final JwtUtil pJwtUtil) {
        this.authenticationManager = pAuthManager;
        this.userRepo = pUserRepo;
        this.passwordEncoder = pPassEncoder;
        this.jwtUtil = pJwtUtil;
    }

    /**
     * Registers a new user in the system.
     *
     * @param pUser The user to be registered.
     * @return A TokenResponse containing the JWT and refresh token.
     * @throws LabReserveException If an error occurs during registration.
     */
    public TokenResponse register(final User pUser)
            throws LabReserveException {
        try {
            UserMongodb userMongo = new UserMongodb(pUser);
            userMongo.setPassword(passwordEncoder.encode(pUser.getPassword()));
            userRepo.save(userMongo);
            String jwtToken = jwtUtil.generateToken(userMongo);
            String refreshToken = jwtUtil.generateRefreshToken(userMongo);
            return new TokenResponse(jwtToken, refreshToken);
        } catch (Exception e) {
            throw new LabReserveException("Error al crear el usuario: "
                    + e.getMessage());
        }
    }

    /**
     * Authenticates a user based on provided credentials.
     *
     * @param pAuthRequest The authentication request containing credentials.
     * @return A TokenResponse containing tokens.
     */
    public final TokenResponse login(final AuthRequest pAuthRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        pAuthRequest.getEmail(),
                        pAuthRequest.getPassword()
                )
        );
        UserMongodb userMongodb = userRepo.findByMail(pAuthRequest.getEmail());
        String token = jwtUtil.generateToken(userMongodb);
        String refreshToken = jwtUtil.generateRefreshToken(userMongodb);
        return new TokenResponse(token, refreshToken);
    }
}
