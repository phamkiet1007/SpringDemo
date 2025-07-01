package vn.hoidanit.todo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.hoidanit.todo.entity.User;
import vn.hoidanit.todo.repository.UserRepository;
import vn.hoidanit.todo.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    public void createUser_shouldReturnUser_WhenEmailIsValid() {
        //arrange: chuẩn bị
        User inputUser = new User(null, "eric", "hoidanit@gmail.com");
        User outputUser = new User(1L, "eric", "hoidanit@gmail.com");

        when(this.userRepository.existsByEmail(inputUser.getEmail())).thenReturn(false);

        when(this.userRepository.save(any())).thenReturn(outputUser);

        //act: hành động
        User result = this.userServiceImpl.createUser(inputUser);

        //assert: so sánh
        assertEquals(1L, result.getId());
    }

    @Test
    public void createUser_shouldThrowException_WhenEmailIsValid() {
        //arrange: chuẩn bị
        User inputUser = new User(null, "eric", "hoidanit@gmail.com");
        User outputUser = new User(1L, "eric", "hoidanit@gmail.com");

        when(this.userRepository.existsByEmail(inputUser.getEmail())).thenReturn(true);


        //act: hành động
        Exception ex = assertThrows(IllegalArgumentException.class, () -> this.userServiceImpl.createUser(inputUser));

        //assert: so sánh
        assertEquals("Email already exists", ex.getMessage());
    }

    @Test
    public void getAllUsers_shouldReturnAllUsers_WhenThereAreUsersInDB() {
        //arrange: chuẩn bị (giả lập kết quả)
        List<User> outputUsers = new ArrayList<>();
        outputUsers.add(new User(1L, "eric", "hoidanit@gmail.com"));
        outputUsers.add(new User(2L, "harry", "hoidanit1@gmail.com"));

        when(this.userRepository.findAll()).thenReturn(outputUsers);

        //act: hành động (kết quả thực)
        List<User> result = this.userServiceImpl.getAllUsers();

        //assert: so sánh
        assertEquals(2, result.size());
        assertEquals("hoidanit@gmail.com", result.get(0).getEmail());

    }

    @Test
    public void getUserById_shouldReturnUser_WhenUserExist() {
        //arrange: chuẩn bị
        Long inputId = 1L;
        Optional<User> userOptionalOutput = Optional.of(new User(1L, "eric", "hoidanit@gmail.com"));
        when(this.userRepository.findById(inputId)).thenReturn(userOptionalOutput);

        //act: hành động
        Optional<User> result = this.userServiceImpl.getUserById(inputId);

        //assert: so sánh
        assertEquals(true, result.isPresent());
    }

    @Test
    public void deleteUser_shouldReturnVoid_WhenUserExist() {
        //arrange: chuẩn bị
        Long inputId = 1L;
        when(this.userRepository.existsById(inputId)).thenReturn(true);
        //act: hành động
        this.userServiceImpl.deleteUser(inputId);

        //assert: so sánh
        verify(this.userRepository, times(1)).deleteById(inputId);
    }

    @Test
    public void deleteUser_shouldThrowException_WhenUserNotExist() {
        //arrange: chuẩn bị
        Long inputId = 1L;
        when(this.userRepository.existsById(inputId)).thenReturn(false);
        //act: hành động
        Exception ex = assertThrows(NoSuchElementException.class, () -> this.userServiceImpl.deleteUser(inputId));

        //assert: so sánh
        assertEquals("User not found", ex.getMessage());

    }

    @Test
    public void updateUser_shouldReturnUpdatedUser_WhenUserExist() {
        //arrange: chuẩn bị
        Long inputId = 1L;
        User inputUser = new User(1L, "old name", "old@gmail.com");
        User outputUser = new User(1L, "new name", "new@gmail.com");
        when(this.userRepository.findById(inputId)).thenReturn(Optional.of(inputUser));
        when(this.userRepository.save(any())).thenReturn(outputUser);
        //act: hành động
        User result = this.userServiceImpl.updateUser(inputId, inputUser);

        //assert: so sánh
        assertEquals("new name", result.getName());

    }
}
