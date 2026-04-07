package edu.eci.cvds.labReserves.controller;

import edu.eci.cvds.labReserves.dto.AuthRequest;
import edu.eci.cvds.labReserves.dto.TokenResponse;
import edu.eci.cvds.labReserves.model.LabReserveException;
import edu.eci.cvds.labReserves.model.User;
import edu.eci.cvds.labReserves.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private User testUser;
    private AuthRequest authRequest;
    private TokenResponse tokenResponse;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setMail("testuser@test.com");

        authRequest = new AuthRequest("testuser", "password");
        tokenResponse = new TokenResponse("test-token", "some-role");
    }

    @Test
    void testRegisterUser() throws LabReserveException {
        when(authService.register(any(User.class))).thenReturn(tokenResponse);

        ResponseEntity<TokenResponse> response = authController.registerUser(testUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tokenResponse, response.getBody());
        verify(authService, times(1)).register(testUser);
    }

    @Test
    void testLoginUser() throws Exception {
        when(authService.login(any(AuthRequest.class))).thenReturn(tokenResponse);

        ResponseEntity<TokenResponse> response = authController.loginUser(authRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tokenResponse, response.getBody());
        verify(authService, times(1)).login(authRequest);
    }
}
