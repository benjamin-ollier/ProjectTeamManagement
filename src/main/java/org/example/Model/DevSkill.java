package org.example.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "devSkill")
public class DevSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int devSkillId;
    @ManyToOne
    @JoinColumn(name = "teamId", referencedColumnName = "teamId")
    private Team teamId;
    @ManyToOne
    @JoinColumn(name = "userEmail", referencedColumnName = "email")
    private User userEmail;
    @ManyToOne
    @JoinColumn(name = "userName", referencedColumnName = "name")
    private User userName;
    @ManyToOne
    @JoinColumn(name = "techId", referencedColumnName = "techId")
    private Technology techId;
    @Column(name = "yearsOfExperience")
    private int yearsOfExperience;
    @Column(name = "niveau")
    private String niveau;

    public DevSkill(int devSkillId, Team teamId, User userEmail, User userName, Technology techId, int yearsOfExperience) {
        this.devSkillId = devSkillId;
        this.teamId = teamId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.techId = techId;
        this.yearsOfExperience = yearsOfExperience;
    }

    public DevSkill() {

    }

    public int getDevSkillId() {
        return devSkillId;
    }

    public void setDevSkillId(int devSkillId) {
        this.devSkillId = devSkillId;
    }

    public Team getTeamId() {
        return teamId;
    }

    public void setTeamId(Team teamId) {
        this.teamId = teamId;
    }

    public User getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(User userEmail) {
        this.userEmail = userEmail;
    }

    public User getUserName() {
        return userName;
    }

    public void setUserName(User userName) {
        this.userName = userName;
    }

    public Technology getTechId() {
        return techId;
    }

    public void setTechId(Technology techId) {
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