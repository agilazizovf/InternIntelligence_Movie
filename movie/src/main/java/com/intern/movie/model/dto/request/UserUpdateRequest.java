package com.intern.movie.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateRequest {

    @Size(min = 1, max = 20, message = "Username must be between 1 and 20 characters")
    @NotEmpty(message = "Username must be not empty")
    @NotBlank(message = "Username is required")
    private String username;
}
