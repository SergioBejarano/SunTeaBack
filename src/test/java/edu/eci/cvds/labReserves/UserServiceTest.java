package edu.eci.cvds.labReserves;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import edu.eci.cvds.labReserves.collections.UserMongodb;
import edu.eci.cvds.labReserves.model.LabReserveException;
import edu.eci.cvds.labReserves.model.User;
import edu.eci.cvds.labReserves.repository.mongodb.UserMongoRepository;
import edu.eci.cvds.labReserves.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserMongoRepository userRepo;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserMongodb userMongo;

    @BeforeEach
    void setUp() throws LabReserveException {
        user = new User(1, "Juan", "juan@mail.com","xd", "admin");
        userMongo = new UserMongodb(user);

    }

    /**
     * Test that a user can be created
     * @throws LabReserveException
     */
    @Test
    void testCreateUser() throws LabReserveException{
        when(userRepo.save(any(UserMongodb.class))).thenReturn(userMongo);
        UserMongodb result = userService.createUser(user);
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getMail(), result.getMail());
        verify(userRepo, times(1)).save(any(UserMongodb.class));
    }

    /**
     * Test a User can not be created
     */
    @Test
    void testCreateUserThrowsException() {
        when(userRepo.save(any(UserMongodb.class))).thenThrow(new RuntimeException("user not created"));
        assertThrows(LabReserveException.class, () -> userService.createUser(user));
    }

    /**
     * Test a user is found by id
     */
    @Test
    void testFindUserById_UserExists() {
        when(userRepo.findById(1)).thenReturn(userMongo);
        Optional<User> result = userService.findUserById(1);
        assertTrue(result.isPresent(), "El usuario debería estar presente");
        assertEquals(user, result.get(), "El usuario devuelto no coincide");
        verify(userRepo, times(1)).findById(1);
    }

    /**
     * Test a user is not found by id
     */
    @Test
    void testFindUserById_UserNotExists() {
        when(userRepo.findById(2)).thenReturn(null);
        Optional<User> result = userService.findUserById(2);
        assertFalse(result.isPresent(), "El usuario NO debería estar presente");
        verify(userRepo, times(1)).findById(2);
    }


    /**
     * Test a user is found by Mail
     */
    @Test
    void testFindUserByMail_UserExists() {
        when(userRepo.findByMail("juan@mail.com")).thenReturn(userMongo);
        Optional<User> result = userService.findUserByMail("juan@mail.com");
        assertTrue(result.isPresent(), "El usuario debería estar presente");
        assertEquals(user, result.get(), "El usuario devuelto no coincide");
        verify(userRepo, times(1)).findByMail("juan@mail.com");
    }

    /**
     * Test a user is not found by id
     */
    @Test
    void testFindUserByMail_UserNotExists() {
        when(userRepo.findById(2)).thenReturn(null);
        Optional<User> result = userService.findUserById(2);
        assertFalse(result.isPresent(), "El usuario NO debería estar presente");
        verify(userRepo, times(1)).findById(2);
    }

}

