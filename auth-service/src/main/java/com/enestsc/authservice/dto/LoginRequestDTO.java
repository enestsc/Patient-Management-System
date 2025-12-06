package com.enestsc.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequestDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be a valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password min 8 char")
    private String password;

    public @NotBlank(message = "Email is required") @Email(message = "Email should be a valid") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email is required") @Email(message = "Email should be a valid") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Password is required") @Size(min = 8, message = "Password min 8 char") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password is required") @Size(min = 8, message = "Password min 8 char") String password) {
        this.password = password;
    }
}
