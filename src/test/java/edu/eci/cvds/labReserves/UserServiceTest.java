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

    @Test
    void testCreateUser() throws LabReserveException{
        when(userRepo.save(any(UserMongodb.class))).thenReturn(userMongo);

        UserMongodb result = userService.createUser(user);

        assertNotNull(result);
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getMail(), result.getMail());

        verify(userRepo, times(1)).save(any(UserMongodb.class));
    }

    @Test
    void testCreateUserThrowsException() {
        // Simular que el repositorio lanza una excepción
        when(userRepo.save(any(UserMongodb.class))).thenThrow(new RuntimeException("Error en la base de datos"));

        // Act & Assert
        assertThrows(LabReserveException.class, () -> userService.createUser(user));
    }
}

