package com.eg.HousingLibrary.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Valid
public class UserDTO {

    @Min(1) // Ensures ID is at least 1
    @Max(99999) // Optional: Ensures ID is within a reasonable range
    private Integer id;
    @NotBlank(message="Name can't be blank")
    private String name;
    @Email(message="Invalid format")
    private String email;
    private String image_file;
    @Min(5)
    private String password;


}
