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


@Service
public class JwtUtil {

    private String SECRET_KEY = "CVDSECIRESERVE2025PAULAALEJANDROJUANSANTIAGO";
    private int EXPIRATION_TIME = 86400000;
    private int REFRESH_TIME = 604800000;

    public String extractUserEmail(String token) {
        Claims jwtToken = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getSubject();
    }

    public String generateToken(UserMongodb user) {
        return createToken(user, EXPIRATION_TIME);
    }

    public String generateRefreshToken(UserMongodb user) {
        return createToken(user, REFRESH_TIME);
    }

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

    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(String token, UserMongodb user){
        String email = extractUserEmail(token);
        return (email.equals(user.getMail())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        Claims jwtToken = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getExpiration();
    }

}
