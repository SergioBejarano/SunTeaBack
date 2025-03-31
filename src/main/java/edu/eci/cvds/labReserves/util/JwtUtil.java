package edu.eci.cvds.labReserves.util;

import edu.eci.cvds.labReserves.collections.UserMongodb;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

/**
 * JwtUtil is a utility class responsible for generating, validating, and extracting data from JWT tokens.
 */
@Service
public class JwtUtil {

    private String SECRET_KEY = "CVDSECIRESERVE2025PAULAALEJANDROJUANSANTIAGO"; //Secret key used for signing JWT tokens.
    private int EXPIRATION_TIME = 86400000; //Expiration time for the access token (1 day in milliseconds).
    private int REFRESH_TIME = 604800000; //Expiration time for the refresh token (7 days in milliseconds).

    /**
     * Extracts the user's email from the given JWT token.
     *
     * @param token The JWT token.
     * @return The email associated with the token.
     */
    public String extractUserEmail(String token) {
        Claims jwtToken = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getSubject();
    }

    /**
     * Generates an access token for a given user.
     *
     * @param user The user for whom the token is generated.
     * @return A signed JWT access token.
     */
    public String generateToken(UserMongodb user) {
        return createToken(user, EXPIRATION_TIME);
    }

    /**
     * Generates a refresh token for a given user.
     *
     * @param user The user for whom the refresh token is generated.
     * @return A signed JWT refresh token.
     */
    public String generateRefreshToken(UserMongodb user) {
        return createToken(user, REFRESH_TIME);
    }

    /**
     * Creates a signed JWT token with the specified expiration time.
     *
     * @param user       The user for whom the token is generated.
     * @param expiration The expiration time of the token in milliseconds.
     * @return A signed JWT token.
     */
    private String createToken(UserMongodb user, long expiration) {
        return Jwts.builder()
                .id(String.valueOf(user.getId()))
                .claims(Map.of("name", user.getName()))
                .subject(user.getMail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSecretKey())
                .compact();
    }

    /**
     * Retrieves the secret key used for signing JWT tokens.
     *
     * @return The secret key.
     */
    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Validates whether a token is valid for the given user.
     *
     * @param token The JWT token to validate.
     * @param user  The user associated with the token.
     * @return True if the token is valid, false otherwise.
     */
    public boolean isTokenValid(String token, UserMongodb user){
        String email = extractUserEmail(token);
        return (email.equals(user.getMail())) && !isTokenExpired(token);
    }

    /**
     * Checks if a JWT token has expired.
     *
     * @param token The JWT token to check.
     * @return True if the token has expired, false otherwise.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from a JWT token.
     *
     * @param token The JWT token.
     * @return The expiration date of the token.
     */
    private Date extractExpiration(String token) {
        Claims jwtToken = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getExpiration();
    }

}
