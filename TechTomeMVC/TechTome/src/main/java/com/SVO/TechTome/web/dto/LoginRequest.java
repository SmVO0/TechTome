package com.SVO.TechTome.web.dto;


import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Data
public class LoginRequest {

    @Email(message = "Email must be valid")
    private String email;

    @Size(min = 8, message = "Password must be at least 8 symbols")
    private String password;
}
