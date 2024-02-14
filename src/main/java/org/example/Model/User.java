package org.example.Model;

import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
@Table(name = "user")
public class User {
    @EmbeddedId
    private UserId userIdentifiant;
    @Column(name = "role")
    private String role;
    @Column(name = "lastTeamChangeDate")
    private LocalDate lastTeamChangeDate;

    public User() {
    }


    public User(UserId userIdentifiant, String role, LocalDate lastTeamChangeDate) {
        this.userIdentifiant = userIdentifiant;
        this.role = role;
        this.lastTeamChangeDate = lastTeamChangeDate;
    }

    public UserId getUserIdentifiant() {
        return userIdentifiant;
    }

    public void setUserIdentifiant(UserId userId) {
        this.userIdentifiant = userId;
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
