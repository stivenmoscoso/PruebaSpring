package com.riwi.talentboard.dto;

import com.riwi.talentboard.enums.Role;
import jakarta.validation.constraints.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class RegisterRequestDTO {
    @NotBlank(message = "Username is required.")
    private String username;

    @NotBlank(message = "Password is required.")
    @Size(min = 6, message = "Password must be at least 6 characters.")
    private String password;

    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email format.")
    private String email;

    @NotNull(message = "Role is required.")
    private Role role; // ADMIN, RECRUITER, CANDIDATE
}