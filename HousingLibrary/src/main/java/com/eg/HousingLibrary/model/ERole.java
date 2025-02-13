package com.eg.HousingLibrary.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.net.ProtocolFamily;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public enum ERole {
    @JsonProperty("USER")
    USER,
    @JsonProperty("ADMIN")
    ADMIN;

   public static List<String> getNames(){
       return Arrays.stream(ERole.values())
               .map(Enum::name)
               .collect(Collectors.toList());
   }

    @JsonCreator
    public static ERole fromString(String value) {
        for (ERole role : ERole.values()) {
            if (role.name().equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role: " + value);
    }

    @JsonValue
    public String getName() {
        return this.name();
    }



    public boolean equalsIgnoreCase(String admin) {
        if (admin== null) {
            return false;
        }
        return this.name().equalsIgnoreCase("ADMIN");
    }
}
