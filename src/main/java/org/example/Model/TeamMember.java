package org.example.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "teamMember")
public class TeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamMemberId;
    @ManyToOne
    @JoinColumn(name = "teamId", referencedColumnName = "teamId")
    private Team teamId;
    @ManyToOne
    @JoinColumn(name = "userEmail", referencedColumnName = "email")
    private User userEmail;
    @ManyToOne
    @JoinColumn(name = "userName", referencedColumnName = "name")
    private User userName;
    @Column(name = "roleInTeam")
    private String roleInTeam;

    public TeamMember(Long teamMemberId, Team teamId, User userEmail, User userName, String roleInTeam) {
        this.teamMemberId = teamMemberId;
        this.teamId = teamId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.roleInTeam = roleInTeam;
    }

    public TeamMember() {

    }

    public Long getTeamMemberId() {
        return teamMemberId;
    }

    public void setTeamMemberId(Long teamMemberId) {
        this.teamMemberId = teamMemberId;
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

    public String getRoleInTeam() {
        return roleInTeam;
    }

    public void setRoleInTeam(String roleInTeam) {
        this.roleInTeam = roleInTeam;
    }
}
