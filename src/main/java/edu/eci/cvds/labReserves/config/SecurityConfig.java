package edu.eci.cvds.labReserves.config;

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
 * SecurityConfig is responsible for configuring
 * security settings for the application.
 * It defines authentication mechanisms, session policies,
 * and request authorization rules.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    /** Filter that processes JWT authentication for incoming requests. */
    private final JwtRequestFilter jwtRequestFilter;

    /** Authentication provider for handling user authentication. */
    private final AuthenticationProvider authenticationProvider;

    /**
     * Constructor to initialize dependencies.
     *
     * @param jwtFilter JWT authentication filter.
     * @param authProvider Authentication provider.
     */
    public SecurityConfig(
            final JwtRequestFilter jwtFilter,
            final AuthenticationProvider authProvider) {
        this.jwtRequestFilter = jwtFilter;
        this.authenticationProvider = authProvider;
    }

    /**
     * Defines the security filter chain, setting authentication rules,
     * disabling CSRF, and configuring session management.
     *
     * @param http The HttpSecurity object to configure security settings.
     * @return The configured SecurityFilterChain.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            final HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/api/**")
                                .permitAll()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(
                        jwtRequestFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/auth/logout")
                                .logoutSuccessHandler(
                                        (request, response, authentication) ->
                                        SecurityContextHolder.clearContext())
                );
        return http.build();
    }
}
