package org.example.Model;

import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique=true)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "role")
    private String role;

    @Column(name = "lastTeamChangeDate")
    private LocalDate lastTeamChangeDate;

    public User() {
    }

    public User(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
