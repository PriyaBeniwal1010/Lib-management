package com.eg.HousingLibrary.utility;



import com.eg.HousingLibrary.dto.BookDTO;
import com.eg.HousingLibrary.dto.BookLendingDTO;
import com.eg.HousingLibrary.dto.UserDTO;
import com.eg.HousingLibrary.model.Book;
import com.eg.HousingLibrary.model.BookLending;
import com.eg.HousingLibrary.model.User;
import lombok.Data;

@Data
public class EntityDTOMapper {

    /**
     * Convert User entity to UserDTO
     */
    public static UserDTO toUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getid());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    /**
     * Convert UserDTO to User entity
     */
    public static User toUserEntity(UserDTO dto) {
        User user = new User();
        user.setid(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        return user;
    }

    /**
     * Convert Book entity to BookDTO
     */
    public static BookDTO toBookDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        return dto;
    }

    /**
     * Convert BookDTO to Book entity
     */
    public static Book toBookEntity(BookDTO dto) {
        Book book = new Book();
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        return book;
    }

    /**
     * Convert BookLending entity to BookLendingDTO
     */
    public static BookLendingDTO toBookLendingDTO(BookLending bookLending) {
        BookLendingDTO dto = new BookLendingDTO();
        dto.setId(bookLending.getId());
        dto.setUserId(bookLending.getUser().getid());
        dto.setBookId(bookLending.getBook().getId());
        dto.setBorrowedDate(bookLending.getBorrowedDate());
        dto.setDueDate(bookLending.getDueDate());
        dto.setReturnedDate(bookLending.getReturnedDate());
        return dto;
    }

    /**
     * Convert BookLendingDTO to BookLending entity (User and Book should be set separately)
     */
    public static BookLending toBookLendingEntity(BookLendingDTO dto) {
        BookLending bookLending = new BookLending();
        bookLending.setId(dto.getId());
        bookLending.setBorrowedDate(dto.getBorrowedDate());
        bookLending.setDueDate(dto.getDueDate());
        bookLending.setReturnedDate(dto.getReturnedDate());
        return bookLending;
    }
}
