package edu.eci.cvds.labReserves.services;

import edu.eci.cvds.labReserves.collections.UserMongodb;
import edu.eci.cvds.labReserves.repository.mongodb.UserMongoRepository;
import edu.eci.cvds.labReserves.model.User;
import edu.eci.cvds.labReserves.model.LabReserveException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The {@code UserService} class provides business logic for managing
 * users within the application.
 */
@Service
public class UserService {

    /** The repository for user data access. */
    private final UserMongoRepository userRepo;

    /**
     * Constructor for UserService.
     * @param pUserRepo The user repository.
     */
    public UserService(final UserMongoRepository pUserRepo) {
        this.userRepo = pUserRepo;
    }

    /**
     * Creates and saves a new user.
     *
     * @param pUser The user object to be saved.
     * @return The saved {@code UserMongodb} object.
     * @throws LabReserveException If an error occurs while creating the user.
     */
    public final UserMongodb createUser(final User pUser)
            throws LabReserveException {
        try {
            UserMongodb userMongo = new UserMongodb(pUser);
            return userRepo.save(userMongo);

        } catch (Exception e) {
            throw new LabReserveException("Error al crear el usuario: "
                    + e.getMessage());
        }
    }

    /**
     * Searches for a user by ID.
     *
     * @param pUserId The user ID.
     * @return An {@code Optional} containing the found user.
     */
    public final Optional<User> findUserById(final int pUserId) {
        return Optional.ofNullable(userRepo.findById(pUserId));
    }

    /**
     * Searches for a user by email.
     *
     * @param pEmail The user's email address.
     * @return An {@code Optional} containing the found user.
     */
    public final Optional<UserMongodb> findUserByMail(final String pEmail) {
        return Optional.ofNullable(userRepo.findByMail(pEmail));
    }

    /**
     * Searches for a user by name.
     *
     * @param pName The user's name.
     * @return An {@code Optional} containing the found user.
     */
    public final Optional<User> findUserByName(final String pName) {
        return Optional.ofNullable(userRepo.findByName(pName));
    }

    /**
     * Changes the user's password.
     *
     * @param pNewPassword The new password to be set.
     * @param pUser The user whose password is being changed.
     * @return The updated user object.
     * @throws LabReserveException If an error occurs.
     */
    public final User changeUserPassword(final String pNewPassword,
            final User pUser) throws LabReserveException {
        pUser.setPassword(pNewPassword);
        UserMongodb userMongodb = new UserMongodb(pUser);
        return userRepo.save(userMongodb);
    }

    /**
     * Changes the user's email.
     *
     * @param pNewMail The new email to be set.
     * @param pUser The user whose email is being changed.
     * @return The updated user object.
     * @throws LabReserveException If an error occurs.
     */
    public final User changeUserMail(final String pNewMail,
            final User pUser) throws LabReserveException {
        pUser.setMail(pNewMail);
        UserMongodb userMongodb = new UserMongodb(pUser);
        return userRepo.save(userMongodb);
    }

    /**
     * Changes the user's name.
     *
     * @param pNewName The new name to be set.
     * @param pUser The user whose name is being changed.
     * @return The updated user object.
     * @throws LabReserveException If an error occurs.
     */
    public final User changeUserName(final String pNewName,
            final User pUser) throws LabReserveException {
        pUser.setName(pNewName);
        UserMongodb userMongodb = new UserMongodb(pUser);
        return userRepo.save(userMongodb);
    }

    /**
     * Changes the user's role.
     *
     * @param pNewRol The new role to be set.
     * @param pUser The user whose role is being changed.
     * @return The updated user object.
     * @throws LabReserveException If an error occurs.
     */
    public final User changeUserRol(final String pNewRol,
            final User pUser) throws LabReserveException {
        pUser.setRol(pNewRol);
        UserMongodb userMongodb = new UserMongodb(pUser);
        return userRepo.save(userMongodb);
    }

    /**
     * Deletes a user.
     *
     * @param pUser The user to be deleted.
     * @throws LabReserveException If an error occurs.
     */
    public final void deleteUser(final User pUser) throws LabReserveException {
        userRepo.deleteById(pUser.getId());
    }

    /**
     * Retrieves all users.
     *
     * @return A list of all users.
     */
    public final List<UserMongodb> getAllUsers() {
        return userRepo.findAll();
    }

    /**
     * Retrieves user information by ID.
     *
     * @param pUserId The user ID.
     * @return A list containing the user's name, email, and role.
     * @throws LabReserveException If an error occurs.
     */
    public final List<String> getUserInfor(final int pUserId)
            throws LabReserveException {
        User actualUser = userRepo.findById(pUserId);
        List<String> userInfo = new ArrayList<>();
        userInfo.add(actualUser.getName());
        userInfo.add(actualUser.getMail());
        userInfo.add(actualUser.getRol());
        return userInfo;
    }
}
