package edu.eci.cvds.labReserves.config;

import edu.eci.cvds.labReserves.collections.UserMongodb;
import edu.eci.cvds.labReserves.repository.mongodb.UserMongoRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import edu.eci.cvds.labReserves.security.CustomUserDetailsService;
import edu.eci.cvds.labReserves.util.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Optional;

/**
 * JwtRequestFilter is a security filter that intercepts HTTP requests to validate JWT tokens.
 * It ensures that authenticated users have valid tokens before processing the request.
 */
@Component
@AllArgsConstructor
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil; //Utility class for handling JWT operations.
    private CustomUserDetailsService customUserDetailsService; //Service for retrieving user details.
    private UserDetailsService userDetailsService; //Spring Security service for loading user details.
    private UserMongoRepository userRepo; //Repository for accessing user data from MongoDB.

    /**
     * Filters incoming requests and validates JWT authentication tokens.
     *
     * @param request The HTTP request.
     * @param response The HTTP response.
     * @param filterChain The filter chain to continue request processing.
     * @throws ServletException If an error occurs during request processing.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Skip authentication for /api/auth endpoints
        if (request.getServletPath().contains("/api")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Retrieve the Authorization header
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract token and user email
        final String token = authHeader.substring(7);
        final String email = jwtUtil.extractUserEmail(token);
        if (email == null || SecurityContextHolder.getContext().getAuthentication() == null) {
            return;
        }

        // Load user details from the database
        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        final Optional<UserMongodb> user = userRepo.findById(email);
        if (user.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        // Validate token
        final boolean isTokenValid = jwtUtil.isTokenValid(token, user.get());
        if (!isTokenValid) {
            return;
        }

        // Set authentication in the security context
        final var authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
