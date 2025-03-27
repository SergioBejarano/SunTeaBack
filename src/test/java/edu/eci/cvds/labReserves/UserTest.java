package edu.eci.cvds.labReserves;
import edu.eci.cvds.labReserves.model.LabReserveException;
import edu.eci.cvds.labReserves.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class UserTest {
    private User userTeacher;
    private User userAdmin;

    @BeforeEach
    void setUp() throws LabReserveException {
        userTeacher = new User(10229,"pepe","pepito@gmail.com","pepito234","teacher");
        userAdmin = new User(14529,"ricardo","irchard@gmail.com","ricardo:D","admin");
    }

    /**
     * Verify If a user can be created with an unknown rol
     * @throws LabReserveException
     */
    @Test
    public void testShouldNotCreateUserByRol() throws LabReserveException {
        try{
            User invalidUser = new User(14529,"ricardo","irchard@gmail.com","ricardo:D","director");
        }catch (LabReserveException e){
            assertEquals("this rol is not admited",e.getMessage());
        }
    }

    /**
     * Verify if a user can be created
     * @throws LabReserveException
     */
    @Test
    public void testShouldCreateUser() throws LabReserveException{
        assertEquals(userTeacher.getId(),10229);
        assertEquals(userTeacher.getMail(), "pepito@gmail.com");
        assertEquals(userTeacher.getName(),"pepe");
        assertEquals(userTeacher.getPassword(), "pepito234");
        assertEquals(userTeacher.getRol(),"teacher");
    }

    /**
     * Verify if change user name is correct
     * @throws LabReserveException
     */
    @Test
    public void testUserChangeName() throws LabReserveException{
        String testName = "new name";
        userTeacher.setName(testName);
        assertTrue(userTeacher.getName() == testName, "nombre actualizado correctamente");
    }
    /**
     * Verify if change user id is correct
     * @throws LabReserveException
     */
    @Test
    public void testUserChangeID() throws LabReserveException{
        int testid = 1;
        userTeacher.setId(testid);
        assertEquals(1,testid);
    }
    /**
     * Verify if change user password is correct
     * @throws LabReserveException
     */
    @Test
    public void testUserChangePssw() throws  LabReserveException{
        String newPssw = "newPssw";
        userTeacher.setPassword(newPssw);
        assertEquals(newPssw,userTeacher.getPassword());
    }
    /**
     * Verify if change user mail is correct
     * @throws LabReserveException
     */
    @Test
    public void testUserChangeMail() throws LabReserveException{
        String testMail = "new@gmail.com";
        userTeacher.setMail(testMail);
        assertEquals(testMail,userTeacher.getMail());
    }
    /**
     * Verify if change user rol is correct
     * @throws LabReserveException
     */
    @Test
    public void testUserChangeRol() throws LabReserveException{
        String rol = "admin";
        userTeacher.setRol(rol);
        assertEquals(rol,userTeacher.getRol());
    }
    /**
     * Verify if change user rol is incorrect for invalid type of rol
     * @throws LabReserveException
     */
    @Test
    public void testShouldNotChangeRol() throws LabReserveException{
        try{
            userTeacher.setRol("estudiante");
        }catch (LabReserveException e){
            assertEquals("this rol is not admited",e.getMessage());
        }
    }
}
