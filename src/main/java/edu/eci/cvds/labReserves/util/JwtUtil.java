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
 * JwtUtil is a utility class responsible for generating, validating,
 * and extracting data from JWT tokens.
 */
@Service
public class JwtUtil {

    /** Secret key used for signing JWT tokens. */
    private static final String SECRET_KEY = "CVDSECIRESERVE2025"
            + "PAULAALEJANDROJUANSANTIAGO"; // pragma: allowlist secret

    /** Expiration time for the access token (1 day in milliseconds). */
    private static final int EXPIRATION_TIME = 86400000;

    /** Expiration time for the refresh token (7 days in milliseconds). */
    private static final int REFRESH_TIME = 604800000;

    /**
     * Extracts the user's email from the given JWT token.
     *
     * @param pToken The JWT token.
     * @return The email associated with the token.
     */
    public final String extractUserEmail(final String pToken) {
        Claims jwtToken = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(pToken)
                .getPayload();
        return jwtToken.getSubject();
    }

    /**
     * Generates an access token for a given user.
     *
     * @param pUser The user for whom the token is generated.
     * @return A signed JWT access token.
     */
    public final String generateToken(final UserMongodb pUser) {
        return createToken(pUser, EXPIRATION_TIME);
    }

    /**
     * Generates a refresh token for a given user.
     *
     * @param pUser The user for whom the refresh token is generated.
     * @return A signed JWT refresh token.
     */
    public final String generateRefreshToken(final UserMongodb pUser) {
        return createToken(pUser, REFRESH_TIME);
    }

    /**
     * Creates a signed JWT token with the specified expiration time.
     *
     * @param pUser       The user for whom the token is generated.
     * @param pExpiration The expiration time of the token in milliseconds.
     * @return A signed JWT token.
     */
    private String createToken(
        final UserMongodb pUser,
        final long pExpiration) {
        return Jwts.builder()
                .id(String.valueOf(pUser.getId()))
                .claims(Map.of("name", pUser.getName()))
                .subject(pUser.getMail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + pExpiration))
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
     * @param pToken The JWT token to validate.
     * @param pUser  The user associated with the token.
     * @return True if the token is valid, false otherwise.
     */
    public final boolean isTokenValid(
            final String pToken,
            final UserMongodb pUser) {
        String email = extractUserEmail(pToken);
        boolean isExpired = isTokenExpired(pToken);
        return email.equals(pUser.getMail()) && !isExpired;
    }

    /**
     * Checks if a JWT token has expired.
     *
     * @param pToken The JWT token to check.
     * @return True if the token has expired, false otherwise.
     */
    private boolean isTokenExpired(final String pToken) {
        return extractExpiration(pToken).before(new Date());
    }

    /**
     * Extracts the expiration date from a JWT token.
     *
     * @param pToken The JWT token.
     * @return The expiration date of the token.
     */
    private Date extractExpiration(final String pToken) {
        Claims jwtToken = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(pToken)
                .getPayload();
        return jwtToken.getExpiration();
    }
}
