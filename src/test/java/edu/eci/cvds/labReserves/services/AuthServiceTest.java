package edu.eci.cvds.labReserves.services;

import edu.eci.cvds.labReserves.collections.UserMongodb;
import edu.eci.cvds.labReserves.config.RestTemplateConf;
import edu.eci.cvds.labReserves.dto.AuthRequest;
import edu.eci.cvds.labReserves.dto.TokenResponse;
import edu.eci.cvds.labReserves.model.LabReserveException;
import edu.eci.cvds.labReserves.model.User;
import edu.eci.cvds.labReserves.repository.mongodb.UserMongoRepository;
import edu.eci.cvds.labReserves.security.CustomUserDetailsService;
import edu.eci.cvds.labReserves.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private UserMongoRepository userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private RestTemplateConf restTemplateConf;

    @InjectMocks
    private AuthService authService;

    private User user;
    private UserMongodb userMongodb;
    private AuthRequest authRequest;

    @BeforeEach
    void setUp() throws LabReserveException {
        user = new User(12345, "password", "Name", "12345", "teacher");
        userMongodb = new UserMongodb(user);
        authRequest = new AuthRequest("test@mail.com", "password");
    }

    @Test
    void register() throws LabReserveException {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepo.save(any(UserMongodb.class))).thenReturn(userMongodb);
        when(jwtUtil.generateToken(any(UserMongodb.class))).thenReturn("jwt-token");
        when(jwtUtil.generateRefreshToken(any(UserMongodb.class))).thenReturn("refresh-token");

        TokenResponse result = authService.register(user);

        assertNotNull(result);
        assertEquals("jwt-token", result.getJwtToken());
        assertEquals("refresh-token", result.getRefreshToken());
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepo, times(1)).save(any(UserMongodb.class));
    }

    @Test
    void register_Exception() {
        when(passwordEncoder.encode(anyString())).thenThrow(new RuntimeException("DB error"));

        LabReserveException exception = assertThrows(LabReserveException.class, () -> authService.register(user));

        assertTrue(exception.getMessage().contains("DB error"));
    }

    @Test
    void login() throws Exception {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userRepo.findByMail("test@mail.com")).thenReturn(userMongodb);
        when(jwtUtil.generateToken(userMongodb)).thenReturn("jwt-token");
        when(jwtUtil.generateRefreshToken(userMongodb)).thenReturn("refresh-token");

        TokenResponse result = authService.login(authRequest);

        assertNotNull(result);
        assertEquals("jwt-token", result.getJwtToken());
        assertEquals("refresh-token", result.getRefreshToken());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepo, times(1)).findByMail("test@mail.com");
    }
}
