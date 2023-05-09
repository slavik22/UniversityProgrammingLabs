package com.uni.twitter.security.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class LoginRequest implements Serializable {

    private static final long serialVersionUID = 5462323531652515527L;

    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
