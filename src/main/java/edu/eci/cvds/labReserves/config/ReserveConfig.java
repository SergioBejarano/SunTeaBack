
package edu.eci.cvds.labReserves.config;

import edu.eci.cvds.labReserves.collections.UserMongodb;
import edu.eci.cvds.labReserves.repository.mongodb.UserMongoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for managing authentication and user repository settings.
 * This class enables MongoDB repositories
 * and provides authentication-related beans.
 */
@Configuration
@EnableMongoRepositories(basePackages =
    "edu.eci.cvds.labReserves.repository.mongodb")
public class ReserveConfig {
    /** Repository for accessing user data from MongoDB. */
    private final UserMongoRepository userMongoRepository;

    /**
     * Constructor that injects the user repository implementation.
     *
     * @param puserMongoRepository The user repository.
     */
    public ReserveConfig(final UserMongoRepository puserMongoRepository) {
        this.userMongoRepository = puserMongoRepository;
    }

    /**
     * Bean definition for the authentication manager.
     *
     * @param config The authentication configuration.
     * @return The authentication manager instance.
     * @throws Exception If an error occurs during authentication setup.
     */
    @Bean
    public AuthenticationManager authenticationManager(
        final AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Bean definition for the authentication provider.
     *
     * @return The configured authentication provider.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider =
            new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * Bean definition for password encoding using BCrypt.
     *
     * @return The password encoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean definition for user details service.
     * It retrieves user details from the database based on email.
     *
     * @return The UserDetailsService implementation.
     */
    @Bean
    public UserDetailsService userDetailService() {
        return name -> {
            UserMongodb user = userMongoRepository.findByMail(name);
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getMail())
                    .password(user.getPassword())
                    .build();
        };
    }
}
