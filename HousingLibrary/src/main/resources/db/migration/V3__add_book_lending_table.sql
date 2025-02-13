CREATE TABLE IF NOT EXISTS book_lending (
                              id INT PRIMARY KEY AUTO_INCREMENT,
                              borrowed_date DATE,
                              due_date DATE,
                              returned_date DATE,
                              book_id INT NOT NULL,
                              user_id INT NOT NULL,
                              CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE,
                              CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);
