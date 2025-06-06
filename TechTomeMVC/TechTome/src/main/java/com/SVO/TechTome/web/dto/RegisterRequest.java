package com.SVO.TechTome.web.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank
    @Size(min = 6, message = "Username must be at least 6 symbols")
    private String username;

    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 symbols")
    private String password;

    @NotBlank
    @Email(message = "Email must be valid")
    private String email;

}
