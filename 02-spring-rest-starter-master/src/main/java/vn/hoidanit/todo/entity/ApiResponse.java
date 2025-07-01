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

package vn.hoidanit.todo.entity;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class ApiResponse<T> {
	private String status;
	private String message;
	private T data;
	private String errorCode;
	private LocalDateTime timestamp = LocalDateTime.now();

	public ApiResponse(HttpStatus httpStatus, String message, T data, String errorCode) {
		this.status = httpStatus.is2xxSuccessful() ? "success" : "error";
		this.message = message;
		this.data = data;
		this.errorCode = errorCode;
		this.timestamp = LocalDateTime.now();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}
