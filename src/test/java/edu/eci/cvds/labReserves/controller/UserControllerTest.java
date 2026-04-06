package edu.eci.cvds.labReserves.controller;

import edu.eci.cvds.labReserves.collections.UserMongodb;
import edu.eci.cvds.labReserves.model.LabReserveException;
import edu.eci.cvds.labReserves.model.User;
import edu.eci.cvds.labReserves.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;
    private UserMongodb userMongodb;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setMail("test@test.com");

        userMongodb = new UserMongodb();
        userMongodb.setId(1);
        userMongodb.setMail("test@test.com");
    }

    @Test
    void testCreateUser() throws LabReserveException {
        when(userService.createUser(any(User.class))).thenReturn(userMongodb);

        UserMongodb result = userController.createUser(user);

        assertEquals(userMongodb, result);
        verify(userService, times(1)).createUser(user);
    }

    @Test
    void testCreateUserException() throws LabReserveException {
        when(userService.createUser(any(User.class))).thenThrow(new LabReserveException("Error"));

        assertThrows(LabReserveException.class, () -> {
            userController.createUser(user);
        });
    }

    @Test
    void testDeleteUserSuccess() throws LabReserveException {
        when(userService.findUserById(1)).thenReturn(Optional.of(user));

        ResponseEntity<String> response = userController.deleteUser(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).deleteUser(user);
    }

    @Test
    void testDeleteUserNotFound() throws LabReserveException {
        when(userService.findUserById(1)).thenReturn(Optional.empty());

        ResponseEntity<String> response = userController.deleteUser(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, never()).deleteUser(any());
    }

    @Test
    void testChangePasswordSuccess() throws LabReserveException {
        when(userService.findUserById(1)).thenReturn(Optional.of(user));

        ResponseEntity<String> response = userController.changePassword("newpass", 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).changeUserPassword("newpass", user);
    }

    @Test
    void testChangePasswordNotFound() throws LabReserveException {
        when(userService.findUserById(1)).thenReturn(Optional.empty());

        ResponseEntity<String> response = userController.changePassword("newpass", 1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testChangeMailSuccess() throws LabReserveException {
        when(userService.findUserById(1)).thenReturn(Optional.of(user));

        ResponseEntity<String> response = userController.changeMail("new@test.com", 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).changeUserMail("new@test.com", user);
    }

    @Test
    void testChangeMailNotFound() throws LabReserveException {
        when(userService.findUserById(1)).thenReturn(Optional.empty());

        ResponseEntity<String> response = userController.changeMail("new@test.com", 1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testChangeUserNameSuccess() throws LabReserveException {
        when(userService.findUserById(1)).thenReturn(Optional.of(user));

        ResponseEntity<String> response = userController.changeUserName("newname", 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).changeUserName("newname", user);
    }

    @Test
    void testChangeUserNameNotFound() throws LabReserveException {
        when(userService.findUserById(1)).thenReturn(Optional.empty());

        ResponseEntity<String> response = userController.changeUserName("newname", 1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testChangeRolSuccess() throws LabReserveException {
        when(userService.findUserById(1)).thenReturn(Optional.of(user));

        ResponseEntity<String> response = userController.changeRol("ADMIN", 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).changeUserRol("ADMIN", user);
    }

    @Test
    void testChangeRolNotFound() throws LabReserveException {
        when(userService.findUserById(1)).thenReturn(Optional.empty());

        ResponseEntity<String> response = userController.changeRol("ADMIN", 1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetAllUsers() {
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(userMongodb));

        ResponseEntity<List<UserMongodb>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserByMailSuccess() throws LabReserveException {
        when(userService.findUserByMail("test@test.com")).thenReturn(Optional.of(userMongodb));

        UserMongodb result = userController.getUserByMail("test@test.com");

        assertEquals(userMongodb, result);
    }

    @Test
    void testGetUserByMailNotFound() {
        when(userService.findUserByMail("test@test.com")).thenReturn(Optional.empty());

        assertThrows(LabReserveException.class, () -> {
            userController.getUserByMail("test@test.com");
        });
    }

    @Test
    void testGetUserInfoByIdSuccess() throws LabReserveException {
        when(userService.findUserById(1)).thenReturn(Optional.of(user));

        User result = userController.getUserInfoById(1);

        assertEquals(user, result);
    }

    @Test
    void testGetUserInfoByIdNotFound() {
        when(userService.findUserById(1)).thenReturn(Optional.empty());

        assertThrows(LabReserveException.class, () -> {
            userController.getUserInfoById(1);
        });
    }
}