package org.example.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "devSkill")
public class DevSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long devSkillId;
    @ManyToOne
    @JoinColumn(name = "teamId", referencedColumnName = "teamId",  nullable = true)
    private Team teamId;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "userIdentityEmail", referencedColumnName = "email"),
            @JoinColumn(name = "userIdentityName", referencedColumnName = "name")
    })
    private User userIdentity;

    @ManyToOne
    @JoinColumn(name = "techId", referencedColumnName = "techId", nullable = false)
    private Technology techId;
    @Column(name = "yearsOfExperience",  nullable = true)
    private int yearsOfExperience;
    @Column(name = "level",  nullable = true)
    private String level;

    public DevSkill(Long devSkillId, Team teamId, User userIdentity, Technology techId, int yearsOfExperience) {
        this.devSkillId = devSkillId;
        this.teamId = teamId;
        this.userIdentity = userIdentity;
        this.techId = techId;
        this.yearsOfExperience = yearsOfExperience;
    }

    public DevSkill() {

    }

    public Long getDevSkillId() {
        return devSkillId;
    }

    public void setDevSkillId(Long devSkillId) {
        this.devSkillId = devSkillId;
    }

    public Team getTeamId() {
        return teamId;
    }

    public void setTeamId(Team teamId) {
        this.teamId = teamId;
    }

    public User getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(User identity) {
        this.userIdentity = identity;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}