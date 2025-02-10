package com.eg.HousingLibrary.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String image_file;

    public Integer getid() {
        return id;
    }

    public void setid(Integer mid) {
        id = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfilePicture(String filePath) {
        this.image_file=filePath;
    }

    public String getProfilePicture(){
        return image_file;
    }

}
