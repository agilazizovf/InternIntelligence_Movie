package com.intern.movie.service;

import com.intern.movie.model.dto.request.UserRequest;
import com.intern.movie.model.dto.request.UserUpdateRequest;
import com.intern.movie.model.dto.response.UserResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<UserResponse> register(UserRequest request);
    ResponseEntity<UserResponse> update(UserUpdateRequest request);
    ResponseEntity<String> delete();
}
