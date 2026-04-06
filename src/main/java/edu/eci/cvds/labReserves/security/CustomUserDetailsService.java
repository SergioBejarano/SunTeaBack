package edu.eci.cvds.labReserves.security;


import edu.eci.cvds.labReserves.collections.UserMongodb;
import edu.eci.cvds.labReserves.repository.mongodb.UserMongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * CustomUserDetailsService is a service that implements Spring Security's UserDetailsService interface.
 * It is responsible for loading user details from the database based on the username.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMongoRepository userRepo; //Repository for interacting with the user database.

    public CustomUserDetailsService(UserMongoRepository userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * Loads a user by their username from the database.
     *
     * @param username The username of the user to be loaded.
     * @return A UserDetails object containing the user's information.
     * @throws UsernameNotFoundException If the user is not found in the database.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserMongodb user = userRepo.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), new ArrayList<>());
    }
}
