package edu.eci.cvds.labReserves.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenResponseTest {

    @Test
    void testTokenResponse() {
        TokenResponse response = new TokenResponse("access123", "refresh456");
        assertEquals("access123", response.accessToken());
        assertEquals("refresh456", response.refreshToken());
        assertEquals("access123", response.getJwtToken());
        assertEquals("refresh456", response.getRefreshToken());
    }
}