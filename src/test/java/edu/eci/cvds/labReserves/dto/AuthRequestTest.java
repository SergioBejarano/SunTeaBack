package edu.eci.cvds.labReserves.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AuthRequestTest {

    @Test
    void testAuthRequest() {
        AuthRequest req = new AuthRequest();
        req.setEmail("test@test.com");
        req.setPassword("pass");

        assertEquals("test@test.com", req.getEmail());
        assertEquals("pass", req.getPassword());

        AuthRequest req2 = new AuthRequest("user@test.com", "1234");
        assertEquals("user@test.com", req2.getEmail());
        assertEquals("1234", req2.getPassword());
    }
}