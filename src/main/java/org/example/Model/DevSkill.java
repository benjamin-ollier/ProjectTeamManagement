package org.example.Model;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class DevSkill {
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User userId;
    @ManyToOne
    @JoinColumn(name = "techId", referencedColumnName = "techId")
    private Technologie techId;
    @Column(name = "yearsOfExperience")
    private int yearsOfExperience;

    @Column(name = "niveau")
    private String niveau;

    public DevSkill(User userId, Technologie techId, int yearsOfExperience, String niveau) {
        this.userId = userId;
        this.techId = techId;
        this.yearsOfExperience = yearsOfExperience;
        this.niveau = niveau;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Technologie getTechId() {
        return techId;
    }

    public void setTechId(Technologie techId) {
        this.techId = techId;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
}
