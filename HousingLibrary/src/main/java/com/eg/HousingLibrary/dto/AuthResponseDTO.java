package com.eg.HousingLibrary.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthResponseDTO {
    private String token;
    private String tokenExpirationTime;

    public AuthResponseDTO(String token, String expirationDate) {
        this.token = token;
        this.tokenExpirationTime = expirationDate;
    }

}
