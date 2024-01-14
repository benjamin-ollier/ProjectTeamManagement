package org.example.Model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class UserId implements Serializable {
    private String name;
    private String email;

    public UserId(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public UserId() {

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
}
