package com.dagdevelop.workTime.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Setter
@Getter
public class User implements Serializable {
    private static final long serialVersionUID = 42L;
    private String username;
    private String fistname;
    private String lastname;
    private String password;
    private String email;
    private String country;
    private String nationality;
    private String gender;
    private Boolean isAdmin;

    @Override
    public String toString() {
        return username;
    }
}
