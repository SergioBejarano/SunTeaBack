package edu.eci.cvds.labReserves.repository.mongodb;

import edu.eci.cvds.labReserves.collections.UserMongodb;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on {@code UserMongodb} documents.
 */
@Repository
public interface UserMongoRepository extends MongoRepository<UserMongodb, String> {

    /**
     * Finds a user by their unique identifier.
     *
     * @param id The user ID.
     * @return The corresponding {@code UserMongodb} object, or {@code null} if not found.
     */
    UserMongodb findById(int id);

    /**
     * Finds a user by their email address.
     *
     * @param mail The user's email.
     * @return The corresponding {@code UserMongodb} object, or {@code null} if not found.
     */
    UserMongodb findByMail(String mail);

    /**
     * Finds users by their role.
     *
     * @param rol The role of the users.
     * @return A list of users with the specified role.
     */
    List<UserMongodb> findByRol(String rol);

    /**
     * Finds a user by their name.
     *
     * @param name The user's name.
     * @return The corresponding {@code UserMongodb} object, or {@code null} if not found.
     */
    UserMongodb findByName(String name);

    /**
     * Deletes a user by their unique identifier.
     *
     * @param id The ID of the user to be deleted.
     */
    void deleteById(int id);

}