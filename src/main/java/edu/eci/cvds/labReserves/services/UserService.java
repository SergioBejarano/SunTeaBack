package edu.eci.cvds.labReserves.services;

import edu.eci.cvds.labReserves.collections.UserMongodb;
import edu.eci.cvds.labReserves.repository.mongodb.*;
import edu.eci.cvds.labReserves.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The {@code UserService} class provides business logic for managing reserves within the application.
 * It handles operations related to User, including creating, updating, deleting, and retrieving.
 */
@Service
public class UserService{

    @Autowired
    private UserMongoRepository userRepo;

    /**
     * Creates and saves a new user.
     *
     * @param user The user object to be saved.
     * @return The saved {@code UserMongodb} object.
     * @throws LabReserveException If an error occurs while creating the user.
     */
    public UserMongodb createUser(User user) throws LabReserveException {
        try{
            UserMongodb userMongo = new UserMongodb(user);
            return userRepo.save(userMongo);
    
        } catch(Exception e){
            throw new LabReserveException("Error al crear el usuario: " + e.getMessage());
        }
    }

    /**
     * Searches for a user by ID.
     *
     * @param user The user ID.
     * @return An {@code Optional} containing the found user or empty if not found.
     */
    public Optional<User> findUserById(int user){
        return Optional.ofNullable(userRepo.findById(user));
    }

    /**
     * Searches for a user by email.
     *
     * @param email The user's email address.
     * @return An {@code Optional} containing the found user or empty if not found.
     */
    public Optional<UserMongodb> findUserByMail(String email){
        return Optional.ofNullable(userRepo.findByMail(email));
    }

    /**
     * Searches for a user by name.
     *
     * @param name The user's name.
     * @return An {@code Optional} containing the found user or empty if not found.
     */
    public Optional<User> findUserByName(String name){
        return Optional.ofNullable(userRepo.findByName(name));
    }

    /**
     * Changes the user's password.
     *
     * @param newPassword The new password to be set.
     * @param user The user whose password is being changed.
     * @return The updated user object.
     * @throws LabReserveException If an error occurs while updating the password.
     */
    public User changeUserPassword(String newPassword, User user) throws LabReserveException {
        user.setPassword(newPassword);
        UserMongodb userMongodb = new UserMongodb(user);
        return userRepo.save(userMongodb);
    }

    /**
     * Changes the user's email.
     *
     * @param newMail The new email to be set.
     * @param user The user whose email is being changed.
     * @return The updated user object.
     * @throws LabReserveException If an error occurs while updating the email.
     */
    public User changeUserMail(String newMail, User user) throws LabReserveException {
        user.setMail(newMail);
        UserMongodb userMongodb = new UserMongodb(user);
        return userRepo.save(userMongodb);
    }

    /**
     * Changes the user's name.
     *
     * @param newName The new name to be set.
     * @param user The user whose name is being changed.
     * @return The updated user object.
     * @throws LabReserveException If an error occurs while updating the name.
     */
    public User changeUserName(String newName, User user) throws LabReserveException {
        user.setName(newName);
        UserMongodb userMongodb = new UserMongodb(user);
        return userRepo.save(userMongodb);
    }

    /**
     * Changes the user's role.
     *
     * @param newRol The new role to be set.
     * @param user The user whose role is being changed.
     * @return The updated user object.
     * @throws LabReserveException If an error occurs while updating the role.
     */
    public User changeUserRol(String newRol, User user) throws LabReserveException {
        user.setRol(newRol);
        UserMongodb userMongodb = new UserMongodb(user);
        return userRepo.save(userMongodb);
    }

    /**
     * Deletes a user.
     *
     * @param user The user to be deleted.
     * @throws LabReserveException If an error occurs while deleting the user.
     */
    public void deleteUser(User user) throws LabReserveException {
        userRepo.deleteById(user.getId());
    }

    /**
     * Retrieves all users.
     *
     * @return A list of all users.
     */
    public List<UserMongodb> getAllUsers() {
        List<UserMongodb> users = userRepo.findAll();
        return users;
    }

    /**
     * Retrieves user information by ID.
     *
     * @param user The user ID.
     * @return A list containing the user's name, email, and role.
     * @throws LabReserveException If an error occurs while retrieving the user information.
     */
    public List<String> getUserInfor(int user) throws LabReserveException{
        User actualUser = userRepo.findById(user);
        List<String> userInfo = new ArrayList<>();
        userInfo.add(actualUser.getName());
        userInfo.add(actualUser.getMail());
        userInfo.add(actualUser.getRol());
        return userInfo;
    }
}