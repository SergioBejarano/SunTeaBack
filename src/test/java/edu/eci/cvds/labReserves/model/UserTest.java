package edu.eci.cvds.labReserves.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User userTeacher;

    @BeforeEach
    void setUp() throws LabReserveException {
        userTeacher = new User(10229,"pepe","pepito@gmail.com","pepito234","teacher");
    }

    /**
     * Verify If a user can be created with an unknown rol
     */
    @Test
    void testShouldNotCreateUserByRol() {
        try{
            User invalidUser = new User(14529,"ricardo","irchard@gmail.com","ricardo:D","director");
            fail("Should have thrown an exception");
        }catch (LabReserveException e){
            assertEquals("this rol is not admited",e.getMessage());
        }
    }

    /**
     * Verify if a user can be created
     */
    @Test
    void testShouldCreateUser() {
        assertEquals(10229, userTeacher.getId());
        assertEquals("pepito@gmail.com", userTeacher.getMail());
        assertEquals("pepe", userTeacher.getName());
        assertEquals("pepito234", userTeacher.getPassword());
        assertEquals("teacher", userTeacher.getRol());
    }

    /**
     * Verify if change user name is correct
     */
    @Test
    void testUserChangeName() {
        String testName = "new name";
        userTeacher.setName(testName);
        assertSame(testName, userTeacher.getName(), "nombre actualizado correctamente");
    }
    /**
     * Verify if change user id is correct
     */
    @Test
    void testUserChangeID() {
        int testid = 1;
        userTeacher.setId(testid);
        assertEquals(1,testid);
    }
    /**
     * Verify if change user password is correct
     */
    @Test
    void testUserChangePssw() {
        String newPssw = "newPssw";
        userTeacher.setPassword(newPssw);
        assertEquals(newPssw,userTeacher.getPassword());
    }
    /**
     * Verify if change user mail is correct
     */
    @Test
    void testUserChangeMail() {
        String testMail = "new@gmail.com";
        userTeacher.setMail(testMail);
        assertEquals(testMail,userTeacher.getMail());
    }
    /**
     * Verify if change user rol is correct
     * @throws LabReserveException
     */
    @Test
    void testUserChangeRol() throws LabReserveException{
        String rol = "admin";
        userTeacher.setRol(rol);
        assertEquals(rol,userTeacher.getRol());
    }
    /**
     * Verify if change user rol is incorrect for invalid type of rol
     */
    @Test
    void testShouldNotChangeRol() {
        try{
            userTeacher.setRol("estudiante");
        }catch (LabReserveException e){
            assertEquals("this rol is not admited",e.getMessage());
        }
    }
}
