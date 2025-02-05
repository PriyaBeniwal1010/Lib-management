package com.example.housing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;


public class EBook extends Book {
    @Setter
    @Getter
    private String downloadLink;
    private boolean  isDRMProtected;

    // Constructor for eBook-specific attributes
    public EBook(int bookID, String bookName,String ISBN, String downloadLink, boolean isDRMProtected) {
        super(bookID, bookName, ISBN);
        this.downloadLink = downloadLink;
        this.isDRMProtected = isDRMProtected;
    }


    @Override
    public String getBookInfo() {
        return super.toString()+ "\n" +
                "Book name"+getBookName()+ "\n" +
                "ISBN"+getISBN()+ "\n" +
                "Download Link: " + downloadLink + "\n"+
                "isDRMProtected: " + isDRMProtected + "\n"+
                "\n.......";
    }

    public boolean isDRMProtected() {
        return isDRMProtected;
    }

    public void setDRMProtected(boolean isDRMProtected) {
        this.isDRMProtected = isDRMProtected;
    }

    @Override
    public String toString() {
        return super.toString()+"\n downloadLink=" + downloadLink + ",\nisDRMProtected=" + isDRMProtected;
    }
}
