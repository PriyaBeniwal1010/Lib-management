package com.eg.HousingLibrary.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Valid
public class UserDTO {

    @Size(min=0, max=100, message="Enter valid no")
    private Integer id;
    @NotBlank(message="Name can't be blank")
    private String name;
    @Email(message="Invalid format")
    private String email;


}
