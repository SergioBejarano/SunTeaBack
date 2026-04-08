package edu.eci.cvds.labReserves.controller;

import edu.eci.cvds.labReserves.collections.UserMongodb;
import edu.eci.cvds.labReserves.services.UserService;
import edu.eci.cvds.labReserves.model.LabReserveException;
import edu.eci.cvds.labReserves.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing user-related operations.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userServ; //Service for handling user operations.

    public UserController(UserService userServ) {
        this.userServ = userServ;
    }

    /**
     * Creates a new user.
     *
     * @param user The user to be created.
     * @return The created user.
     * @throws LabReserveException If an error occurs during user creation.
     */
    @PostMapping("/signin")
    public UserMongodb createUser(@RequestBody User user) throws LabReserveException {
        try{
            return userServ.createUser(user);
        }catch(LabReserveException e){
            throw new LabReserveException(e.getMessage());
        }catch (Exception e) {
            throw new RuntimeException("Error inesperado: " + e.getMessage());
        }
    }

    /**
     * Deletes a user by ID.
     *
     * @param id The ID of the user to be deleted.
     * @return ResponseEntity with success or error message.
     * @throws LabReserveException If an error occurs during deletion.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) throws LabReserveException {
        Optional<User> userOptional = userServ.findUserById(id);

        if (userOptional.isPresent()) {
            userServ.deleteUser(userOptional.get());
            return ResponseEntity.ok("Usuario eliminado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }
    }

    /**
     * Updates a user's password.
     *
     * @param password The new password.
     * @param id The ID of the user whose password will be changed.
     * @return ResponseEntity with success or error message.
     * @throws LabReserveException If an error occurs during the update.
     */
    @PutMapping("/password/{password}")
    public ResponseEntity<String> changePassword(@PathVariable String password, @RequestBody int id) throws LabReserveException {
        Optional<User> userOptional = userServ.findUserById(id);

        if (userOptional.isPresent()) {
            userServ.changeUserPassword(password, userOptional.get());
            return ResponseEntity.ok("Contraseña actualizada correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }
    }

    /**
     * Updates a user's email.
     *
     * @param mail The new email.
     * @param id The ID of the user whose email will be changed.
     * @return ResponseEntity with success or error message.
     * @throws LabReserveException If an error occurs during the update.
     */
    @PutMapping("/mail/{mail}")
    public ResponseEntity<String> changeMail(@PathVariable String mail, @RequestBody int id) throws LabReserveException {
        Optional<User> userOptional = userServ.findUserById(id);

        if (userOptional.isPresent()) {
            userServ.changeUserMail(mail, userOptional.get());
            return ResponseEntity.ok("Correo actualizado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }
    }

    /**
     * Updates a user's name.
     *
     * @param name The new name.
     * @param id The ID of the user whose name will be changed.
     * @return ResponseEntity with success or error message.
     * @throws LabReserveException If an error occurs during the update.
     */
    @PutMapping("/name/{name}")
    public ResponseEntity<String> changeUserName(@PathVariable String name,@RequestBody int id) throws LabReserveException {
        Optional<User> userOptional = userServ.findUserById(id);
        if (userOptional.isPresent()) {
            userServ.changeUserName(name, userOptional.get());
            return ResponseEntity.ok("Nombre actualizado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }
    }

    /**
     * Updates a user's role.
     *
     * @param rol The new role.
     * @param id The ID of the user whose role will be changed.
     * @return ResponseEntity with success or error message.
     * @throws LabReserveException If an error occurs during the update.
     */
    @PutMapping("/rol/{rol}")
    public ResponseEntity<String> changeRol(@PathVariable String rol, @RequestBody int id) throws LabReserveException {
        Optional<User> userOptional = userServ.findUserById(id);

        if (userOptional.isPresent()) {
            userServ.changeUserRol(rol, userOptional.get());
            return ResponseEntity.ok("Rol actualizado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }
    }

    /**
     * Retrieves all users.
     *
     * @return A list of all users.
     */
    @GetMapping("/all")
    public ResponseEntity<List<UserMongodb>> getAllUsers(){
        List<UserMongodb> users = userServ.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Retrieves a user by email.
     *
     * @param mail The email of the user.
     * @return The user with the specified email.
     * @throws LabReserveException If the user is not found.
     */
    @GetMapping("/emails/{mail}")
    public UserMongodb getUserByMail(@PathVariable String mail) throws LabReserveException{
        Optional<UserMongodb> userOptional = userServ.findUserByMail(mail);
        if(userOptional.isPresent()){
            return userOptional.get();
        }else{
            throw new LabReserveException(LabReserveException.USER_NOT_FOUND);
        }
    }

    /**
     * Retrieves user information by ID.
     *
     * @param id The ID of the user.
     * @return The user with the specified ID.
     * @throws LabReserveException If the user is not found.
     */
    @GetMapping("/userinfo/{id}")
    public User getUserInfoById(@PathVariable int id) throws LabReserveException{
        Optional<User> userOptional = userServ.findUserById(id);
        if(userOptional.isPresent()){
            return userOptional.get();
        }else{
            throw new LabReserveException(LabReserveException.USER_NOT_FOUND);
        }
    }
}
