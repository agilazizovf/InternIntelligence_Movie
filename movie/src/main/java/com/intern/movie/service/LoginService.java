package com.intern.movie.service;

import com.intern.movie.model.dto.request.LoginRequest;
import com.intern.movie.model.dto.response.LoginResponse;
import org.springframework.http.ResponseEntity;

public interface LoginService {

    ResponseEntity<?> login(LoginRequest request);
}
