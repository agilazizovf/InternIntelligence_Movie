package com.intern.movie.controller;

import com.intern.movie.model.dto.request.LoginRequest;
import com.intern.movie.model.dto.request.UserRequest;
import com.intern.movie.model.dto.request.UserUpdateRequest;
import com.intern.movie.model.dto.response.UserResponse;
import com.intern.movie.service.LoginService;
import com.intern.movie.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final LoginService loginService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registration(@RequestBody @Valid UserRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        return loginService.login(request);
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponse> update(@RequestBody @Valid UserUpdateRequest request) {
        return userService.update(request);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete() {
        return userService.delete();
    }

}
