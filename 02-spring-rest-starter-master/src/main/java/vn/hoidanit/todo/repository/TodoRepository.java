package vn.hoidanit.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.todo.entity.Todo;

import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    // This interface will automatically provide CRUD operations for the Todo entity
    // No need to define any methods here unless you want custom queries
    Optional<Todo> findByUsername(String username);
}
