package com.example.housing.model;


public class EBook extends Book {
    private String downloadLink;
    private boolean  isDRMProtected;

    // Constructor for eBook-specific attributes
    public EBook(int bookID, String bookName,String ISBN, String downloadLink, boolean isDRMProtected) {
        super(bookID, bookName,ISBN);
        this.downloadLink = downloadLink;
        this.isDRMProtected = false;
    }


    @Override
    public String getBookInfo() {
        return super.tostring()+ "\n" +
                "Download Link: " + downloadLink + "\n"+
                "isDRMProtected: " + isDRMProtected + "\n";
    }

    public boolean isDRMProtected() {
        return isDRMProtected;
    }


    public String getDownloadLink() {
        return downloadLink;
    }
}
