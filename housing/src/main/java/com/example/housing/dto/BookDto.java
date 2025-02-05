package com.example.housing.dto;

import com.example.housing.model.Validation.NotNull;
import com.example.housing.model.Validation.ValidISBN;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BookDto implements Serializable {

    private Integer bookID;
    private String bookName;
    private String ISBN;
    private Integer totalQuantity;  // Total number of books available in the library
    private Integer issuedQuantity; // Number of books already borrowed;
    private Boolean isDRMProtected;
    private String downloadLink;
    private List<String> availableStore;

}
