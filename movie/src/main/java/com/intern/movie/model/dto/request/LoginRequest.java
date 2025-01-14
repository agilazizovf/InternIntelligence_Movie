package com.intern.movie.model.dto.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;

    private String password;
}
