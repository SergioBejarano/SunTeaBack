package edu.eci.cvds.labReserves.util;

import edu.eci.cvds.labReserves.collections.UserMongodb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private UserMongodb user;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();

        user = new UserMongodb();
        user.setId(1);
        user.setMail("test@test.com");
        user.setName("Test User");
    }

    @Test
    void testGenerateToken() {
        String token = jwtUtil.generateToken(user);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void testGenerateRefreshToken() {
        String refreshToken = jwtUtil.generateRefreshToken(user);
        assertNotNull(refreshToken);
        assertFalse(refreshToken.isEmpty());
    }

    @Test
    void testExtractUserEmail() {
        String token = jwtUtil.generateToken(user);
        String extractedEmail = jwtUtil.extractUserEmail(token);
        assertEquals(user.getMail(), extractedEmail);
    }

    @Test
    void testIsTokenValid_validToken() {
        String token = jwtUtil.generateToken(user);
        assertTrue(jwtUtil.isTokenValid(token, user));
    }

    @Test
    void testIsTokenValid_invalidUser() {
        String token = jwtUtil.generateToken(user);

        UserMongodb differenUser = new UserMongodb();
        differenUser.setMail("other@test.com");

        assertFalse(jwtUtil.isTokenValid(token, differenUser));
    }
}