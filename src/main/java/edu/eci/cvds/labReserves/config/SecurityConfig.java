package edu.eci.cvds.labReserves.config;

import edu.eci.cvds.labReserves.security.CustomUserDetailsService;
import edu.eci.cvds.labReserves.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig is responsible for configuring security settings for the application.
 * It defines authentication mechanisms, session policies, and request authorization rules.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private JwtRequestFilter jwtRequestFilter; //Filter that processes JWT authentication for incoming requests.
    private AuthenticationProvider authenticationProvider; //Authentication provider for handling user authentication.

    @Autowired
    private CustomUserDetailsService customUserDetailsService; //Service to load user details.

    @Autowired
    private JwtUtil jwtUtil; //Utility class for handling JWT token operations.

    /**
     * Constructor to initialize dependencies.
     *
     * @param jwtRequestFilter JWT authentication filter.
     * @param authenticationProvider Authentication provider.
     */
    public SecurityConfig(JwtRequestFilter jwtRequestFilter, AuthenticationProvider authenticationProvider) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.authenticationProvider = authenticationProvider;
    }

    /**
     * Defines the security filter chain, setting authentication rules, disabling CSRF,
     * and configuring session management.
     *
     * @param http The HttpSecurity object to configure security settings.
     * @return The configured SecurityFilterChain.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection as JWT is used for authentication
                .csrf(AbstractHttpConfigurer::disable)
                // Define authorization rules
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/api/auth/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                // Set session management policy to stateless as JWT is used
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Set authentication provider
                .authenticationProvider(authenticationProvider)
                // Add JWT filter before processing authentication
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                // Configure logout handling
                .logout(logout ->
                        logout.logoutUrl("/api/auth/logout")
                                .logoutSuccessHandler((request, response, authentication) ->
                                        SecurityContextHolder.clearContext())
                );
        return http.build();
    }
}
