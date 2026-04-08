package edu.eci.cvds.labReserves.security;

import edu.eci.cvds.labReserves.collections.UserMongodb;
import edu.eci.cvds.labReserves.repository.mongodb.UserMongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Service that implements Spring Security's UserDetailsService interface.
 * Loads user details from the database based on the username.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    /** Repository for interacting with the user database. */
    private final UserMongoRepository userRepo;

    /**
     * Constructor for CustomUserDetailsService.
     * @param pUserRepo The user repository dependency.
     */
    public CustomUserDetailsService(final UserMongoRepository pUserRepo) {
        this.userRepo = pUserRepo;
    }

    /**
     * Loads a user by their username from the database.
     *
     * @param pUsername The username of the user to be loaded.
     * @return A UserDetails object containing the user's information.
     * @throws UsernameNotFoundException If the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(final String pUsername)
            throws UsernameNotFoundException {
        UserMongodb user = userRepo.findByName(pUsername);
        if (user == null) {
            throw new UsernameNotFoundException("User " + pUsername
                    + " not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                new ArrayList<>()
        );
    }
}
