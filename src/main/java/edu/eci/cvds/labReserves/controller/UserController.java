package edu.eci.cvds.labReserves.controller;
import edu.eci.cvds.labReserves.collections.UserMongodb;
import edu.eci.cvds.labReserves.services.UserService;
import edu.eci.cvds.labReserves.model.LabReserveException;
import edu.eci.cvds.labReserves.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * @param user
     * @throws LabReserveException
     */
    @DeleteMapping("/delete")
    public void deleteUser(@RequestBody User user) throws LabReserveException{
            userServ.deleteUser(user);
    }

    /**
     *
     * @param password
     * @param user
     * @return
     * @throws LabReserveException
     */
    @PutMapping("/password/{password}")
    public User changePassword(@PathVariable String password, @RequestBody User user) throws LabReserveException {
        return userServ.changeUserPassword(password, user);
    }




    /**
     *
     * @param mail
     * @param user
     * @return
     * @throws LabReserveException
     */
    @PutMapping("/mail/{mail}")
    public User changeMail(@PathVariable String mail,@RequestBody User user) throws LabReserveException {
        return userServ.changeUserMail(mail,user);
    }

    /**
     *
     * @param name
     * @param user
     * @return
     * @throws LabReserveException
     */
    @PutMapping("/name/{name}")
    public User changeUserName(@PathVariable String name,@RequestBody User user) throws LabReserveException {
        return userServ.changeUserName(name,user);
    }

    /**
     *
     * @param rol
     * @param user
     * @return
     * @throws LabReserveException
     */
    @PutMapping("/rol/{rol}")
    public User changeRol(@PathVariable String rol,@RequestBody User user) throws LabReserveException {
        return userServ.changeUserRol(rol,user);
    }





}
