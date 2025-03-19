package edu.eci.cvds.labReserves.controller;
import edu.eci.cvds.labReserves.collections.UserMongodb;
import edu.eci.cvds.labReserves.services.UserService;
import edu.eci.cvds.labReserves.model.LabReserveException;
import edu.eci.cvds.labReserves.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userServ;

    /**
     *
     * @param user
     * @return
     * @throws LabReserveException
     */
    @PostMapping("/signin")
    public UserMongodb createUser(@RequestBody User user) throws LabReserveException {
        try{
            User createdUser = userServ.createUser(user);
            return userServ.createUser(user);
        }catch(LabReserveException e){
            throw new LabReserveException(e.getMessage());
        }catch (Exception e) {
            throw new RuntimeException("Error inesperado: " + e.getMessage());
        }
    }

    /**
     *
     * @param id
     * @throws LabReserveException
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
     *
     * @param password
     * @param id
     * @return
     * @throws LabReserveException
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
     *
     * @param mail
     * @param id
     * @return
     * @throws LabReserveException
     */
    @PutMapping("/mail/{mail}")
    public ResponseEntity<String> changeMail(@PathVariable String mail,@RequestBody int id) throws LabReserveException {
        Optional<User> userOptional = userServ.findUserById(id);

        if (userOptional.isPresent()) {
            userServ.changeUserMail(mail, userOptional.get());
            return ResponseEntity.ok("Correo actualizado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }
    }

    /**
     *
     * @param name
     * @param id
     * @return
     * @throws LabReserveException
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
     *
     * @param rol
     * @param id
     * @return
     * @throws LabReserveException
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
     * Retrieves all users
     * @return list with all users
     * @throws LabReserveException
     */
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() throws LabReserveException{
        List<User> users = userServ.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     *
     * @param id
     * @return
     * @throws LabReserveException
     */
    @GetMapping("/{id}")
    public User getUserByMail(@PathVariable int id) throws LabReserveException{
        Optional<User> userOptional = userServ.findUserById(id);
        if(userOptional.isPresent()){
            return userOptional.get();
        }else{
            throw new LabReserveException(LabReserveException.USER_NOT_FOUND);
        }
    }

    /**
     *
     * @param id
     * @return
     * @throws LabReserveException
     */
    @GetMapping("/userinfo/{id}")
    public List<String> getUserInfoById(@PathVariable int id) throws LabReserveException{
        List<String> userInfo = userServ.getUserInfor(id);
        return userInfo;
    }


}