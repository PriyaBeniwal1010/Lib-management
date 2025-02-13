package com.eg.HousingLibrary.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Valid
public class BookDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotBlank(message = "Title Can't be blank")
    private String title;
    @NotBlank(message = "Author cannot be empty")
    @Size(min = 2, max = 50, message = "Author name must be between 2 and 50 characters")
    private String author;


}
