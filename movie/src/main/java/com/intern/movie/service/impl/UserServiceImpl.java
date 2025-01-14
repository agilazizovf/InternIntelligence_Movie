package com.intern.movie.service.impl;

import com.intern.movie.entity.AuthorityEntity;
import com.intern.movie.entity.UserEntity;
import com.intern.movie.model.dto.request.UserUpdateRequest;
import com.intern.movie.model.exception.AlreadyExistsException;
import com.intern.movie.model.exception.ResourceNotFoundException;
import com.intern.movie.repository.UserRepository;
import com.intern.movie.model.dto.request.UserRequest;
import com.intern.movie.model.dto.response.UserResponse;
import com.intern.movie.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity<UserResponse> register(UserRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new AlreadyExistsException("User already exists!");
        }

        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        AuthorityEntity authorityEntity = new AuthorityEntity("USER");
        Set<AuthorityEntity> authorityEntitySet = Set.of(authorityEntity);
        user.setAuthorities(authorityEntitySet);
        userRepository.save(user);

        UserResponse response = new UserResponse();
        response.setUsername(user.getUsername());
        response.setMessage("User created!");

        log.info("user: {} created successfully", user.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<UserResponse> update(UserUpdateRequest request) {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("Current user from SecurityContext before update: {}", currentUser);

        UserEntity user = userRepository.findByUsername(currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        userRepository.findByUsername(request.getUsername()).ifPresent(existingUser -> {
            if (!existingUser.getId().equals(user.getId())) {
                throw new AlreadyExistsException("User already exists: " + request.getUsername());
            }
        });

        user.setUsername(request.getUsername());
        userRepository.save(user);

        // SecurityContext-in yenilənməsi
        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                null,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        log.debug("SecurityContext updated with new username: {}", user.getUsername());

        UserResponse response = new UserResponse();
        response.setUsername(user.getUsername());
        response.setMessage("User updated!");

        log.info("user: {} updated successfully", user.getUsername());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<String> delete() {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("Current user from SecurityContext: {}", currentUser);

        UserEntity user = userRepository.findByUsername(currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        userRepository.delete(user);
        SecurityContextHolder.clearContext();

        log.info("user: {} deleted successfully", user.getUsername());

        return ResponseEntity.ok("Account successfully deleted and logged out.");
    }

}
