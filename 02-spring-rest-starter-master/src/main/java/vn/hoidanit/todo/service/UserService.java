package vn.hoidanit.todo.service;

import vn.hoidanit.todo.entity.User;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public interface UserService {
    User createUser(User user);

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    User updateUser(Long id, User updatedUser);

    void deleteUser(Long id);
}
