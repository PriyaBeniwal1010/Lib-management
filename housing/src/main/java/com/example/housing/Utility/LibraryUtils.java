package com.example.housing.Utility;

import com.example.housing.model.Book;
import com.example.housing.model.Member;

import java.io.*;
import java.util.List;

public class LibraryUtils {
    private static final String DATA_DIR = "/Users/priyabeniwal/Documents/my-project-folder/Lib-management/housing/src/main/java/com/example/housing/Data/";
    public static final String LIBRARY_FILE = DATA_DIR + "library.ser";
    public static final String BOOKS_FILE = DATA_DIR + "books.ser";
    public static final String MEMBERS_FILE = DATA_DIR + "members.ser";

//    static {
//        File dataDir = new File(DATA_DIR);
//        if (!dataDir.exists()) {
//            dataDir.mkdir();  // Creates the directory if it doesn't exist
//        }
//    }

    // Serialize both books and members into the same file
    public static void serializeLibrary(List<Book> bookList, List<Member> memberList) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(LIBRARY_FILE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(bookList);
            oos.writeObject(memberList);
            oos.flush();
        }
        System.out.println("Library serialized");
    }

    // Deserialize library (books and members) from the same file
    public static void deserializeLibrary() throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(LIBRARY_FILE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            List<Book> bookList = (List<Book>) ois.readObject();
            List<Member> memberList = (List<Member>) ois.readObject();
            System.out.println("Book List: " + bookList);
            System.out.println("Member List: " + memberList);
        }
        System.out.println("Library deserialized");
    }

    // Serialize books list to a file
    public static void serializeBookList(List<Book> bookList) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(BOOKS_FILE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(bookList);
            oos.flush();
        }
        System.out.println("Book List serialized");
    }

    // Deserialize books list from a file
    public static List<Book> deserializeBookList() throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(BOOKS_FILE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (List<Book>) ois.readObject();
        }
    }

    // Serialize members list to a file
    public static void serializeMemberList(List<Member> memberList) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(MEMBERS_FILE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(memberList);
            oos.flush();
        }
        System.out.println("Member List serialized");
    }

    // Deserialize members list from a file
    public static List<Member> deserializeMemberList() throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(MEMBERS_FILE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (List<Member>) ois.readObject();
        }
    }
}
