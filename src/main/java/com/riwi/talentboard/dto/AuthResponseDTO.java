package com.riwi.talentboard.dto;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String username;
    private String role;
}