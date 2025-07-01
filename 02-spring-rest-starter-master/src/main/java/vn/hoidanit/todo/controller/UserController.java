/*
 * Author: Hỏi Dân IT - @hoidanit 
 *
 * This source code is developed for the course
 * "Java Spring RESTful APIs - Xây Dựng Backend với Spring Boot".
 * It is intended for educational purposes only.
 * Unauthorized distribution, reproduction, or modification is strictly prohibited.
 *
 * Copyright (c) 2025 Hỏi Dân IT. All Rights Reserved.
 */

package vn.hoidanit.todo.controller;

import java.util.List;
import java.util.NoSuchElementException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import vn.hoidanit.todo.entity.ApiResponse;
import vn.hoidanit.todo.entity.User;
import vn.hoidanit.todo.service.impl.UserServiceImpl;

@RestController
public class UserController {

	private final UserServiceImpl userServiceImpl;

	public UserController(UserServiceImpl userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}

	@PostMapping("/users")
	public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody User user) {
		User created = userServiceImpl.createUser(user);
		var result = new ApiResponse<>(HttpStatus.CREATED, "User created successfully", created, null);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@GetMapping("/users")
	public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
		ApiResponse<List<User>> result = new ApiResponse<>(HttpStatus.OK, "Success", userServiceImpl.getAllUsers(), null);
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) {
		return userServiceImpl.getUserById(id).map(user -> {
			ApiResponse<User> result = new ApiResponse<>(HttpStatus.OK, "Success", user, null);
			return ResponseEntity.ok().body(result);
		}).orElse(ResponseEntity.notFound().build());

	}

	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
		User updated = userServiceImpl.updateUser(id, user);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<ApiResponse<User>> deleteUser(@PathVariable Long id) {
		userServiceImpl.deleteUser(id);
		ApiResponse<User> result = new ApiResponse<>(HttpStatus.NO_CONTENT, "Delete Successfully", null, null);
		return ResponseEntity.ok(result);
	}
}
