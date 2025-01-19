package com.intern.movie.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {

    @Size(min = 1, max = 20, message = "Username must be between 1 and 20 characters")
    @NotEmpty(message = "Username must be not empty")
    @NotBlank(message = "Username is required")
    private String username;

    @Size(min = 1, message = "Password must be at least 1 character")
    @Size(max = 30, message = "Password must be maximum 30 characters")
    @NotEmpty(message = "Password cannot be empty")
    @NotBlank(message = "Password is required")
    private String password;
}
