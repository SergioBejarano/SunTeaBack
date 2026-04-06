package edu.eci.cvds.labReserves.repository.mongodb;

import edu.eci.cvds.labReserves.collections.UserMongodb;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserMongoRepositoryTest {

    @Mock
    private UserMongoRepository userMongoRepository;

    @Test
    void shouldFindUserById() {
        UserMongodb user = new UserMongodb();
        when(userMongoRepository.findById(1)).thenReturn(user);

        UserMongodb result = userMongoRepository.findById(1);

        assertNotNull(result);
        verify(userMongoRepository, times(1)).findById(1);
    }

    @Test
    void shouldFindUserByMail() {
        UserMongodb user = new UserMongodb();
        when(userMongoRepository.findByMail("test@mail.com")).thenReturn(user);

        UserMongodb result = userMongoRepository.findByMail("test@mail.com");

        assertNotNull(result);
        verify(userMongoRepository, times(1)).findByMail("test@mail.com");
    }

    @Test
    void shouldFindUserByName() {
        UserMongodb user = new UserMongodb();
        when(userMongoRepository.findByName("John")).thenReturn(user);

        UserMongodb result = userMongoRepository.findByName("John");

        assertNotNull(result);
        verify(userMongoRepository, times(1)).findByName("John");
    }

    @Test
    void shouldDeleteUserById() {
        doNothing().when(userMongoRepository).deleteById(1);

        assertDoesNotThrow(() -> userMongoRepository.deleteById(1));
        verify(userMongoRepository, times(1)).deleteById(1);
    }
}