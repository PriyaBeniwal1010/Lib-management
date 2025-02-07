package com.eg.HousingLibrary.dto;

import jakarta.validation.Valid;
import lombok.Data;

@Data
@Valid
public class UserDTO {

    private Integer id;
    private String name;
    private String email;


}
