package org.example.Model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
@Entity
@Table(name = "user")
public class User {
    /*
    @EmbeddedId
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @EmbeddedId
    @Column(name = "name", nullable = false, unique=true)
    private String name;*/

    @EmbeddedId
    private UserId userId;

    @Column(name = "role")
    private String role;

    @Column(name = "lastTeamChangeDate")
    private LocalDate lastTeamChangeDate;

    public User() {
    }


    public User(UserId userId, String role, LocalDate lastTeamChangeDate) {
        this.userId = userId;
        this.role = role;
        this.lastTeamChangeDate = lastTeamChangeDate;
    }

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getLastTeamChangeDate() {
        return lastTeamChangeDate;
    }

    public void setLastTeamChangeDate(LocalDate lastTeamChangeDate) {
        this.lastTeamChangeDate = lastTeamChangeDate;
    }
}
