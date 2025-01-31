package com.example.housing.model;

import com.example.housing.exception.LimitExceedException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;


public class StudentMember extends Member {
    public static final int MAX_BOOKS_ALLOWED = 5;
    //private static final int MAX_BOOKS_ALLOWED = 5;
    int classId;


    public StudentMember(int memberID, String name, String password,
                         HashSet<Book> borrowedBooks, int classID) {
        // Calling the superclass constructor with 'super'
        super(memberID, name, password, borrowedBooks);
        this.classId = classID;
    }




}

