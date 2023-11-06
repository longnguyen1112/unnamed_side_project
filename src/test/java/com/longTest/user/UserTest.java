package com.longTest.user;

import com.longTest.user.Enum.UserTypeEnum;
import com.longTest.user.Users.User;
import com.longTest.user.Users.UserController;
import com.longTest.user.Users.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsersWithoutLastName() {
        List<User> userList = new ArrayList<>();
        userList.add(new User("John", "Doe", 30, "john@example.com", UserTypeEnum.ADMIN));
        userList.add(new User("Jane", "Smith", 25, "jane@example.com", UserTypeEnum.EMPLOYEE));

        Mockito.when(userRepository.findAll()).thenReturn(userList);

        ResponseEntity<List<User>> responseEntity = userController.getAllUsers(null);

        assert responseEntity.getStatusCode().equals(HttpStatus.OK);
        assert responseEntity.getBody().size() == 2;
    }

    @Test
    public void testGetUserById() {
        User user = new User("John", "Doe", 30, "john@example.com", UserTypeEnum.ADMIN);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<User> responseEntity = userController.getUserById(1L);

        assert responseEntity.getStatusCode().equals(HttpStatus.OK);
    }

    @Test
    public void testCreateUser() {
        User userToCreate = new User("Alice", "Johnson", 28, "alice@example.com", UserTypeEnum.EMPLOYEE);
        User savedUser = new User("Alice", "Johnson", 28, "alice@example.com", UserTypeEnum.EMPLOYEE);
        Mockito.when(userRepository.save(userToCreate)).thenReturn(savedUser);

        ResponseEntity<User> responseEntity = userController.createUser(userToCreate);

        assert responseEntity.getStatusCode().equals(HttpStatus.CREATED);
        //assert responseEntity.getBody().equals(savedUser);
    }

    @Test
    public void testUpdateUser() {
        User existingUser = new User("John", "Doe", 30, "john@example.com", UserTypeEnum.ADMIN);
        User updatedUser = new User("Jane", "Smith", 25, "jane@example.com", UserTypeEnum.EMPLOYEE);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        Mockito.when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        ResponseEntity<User> responseEntity = userController.updateUser(1L, updatedUser);

        assert responseEntity.getStatusCode().equals(HttpStatus.OK);
        //assert responseEntity.getBody().equals(updatedUser);
    }

    @Test
    public void testDeleteUser() {
        Mockito.doNothing().when(userRepository).deleteById(1L);

        ResponseEntity<HttpStatus> responseEntity = userController.deleteUser(1L);

        assert responseEntity.getStatusCode().equals(HttpStatus.NO_CONTENT);
    }
}
