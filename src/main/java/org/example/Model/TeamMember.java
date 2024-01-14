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
    @JoinColumns({
            @JoinColumn(name = "userIdentityEmail", referencedColumnName = "email"),
            @JoinColumn(name = "userIdentityName", referencedColumnName = "name")
    })
    private User userIdentity;
    @Column(name = "roleInTeam")
    private String roleInTeam;

    public TeamMember(Long teamMemberId, Team teamId, User userName, String roleInTeam) {
        this.teamMemberId = teamMemberId;
        this.teamId = teamId;
        this.userIdentity = userIdentity;
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

    public User getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(User userEmail) {
        this.userIdentity = userEmail;
    }

    public String getRoleInTeam() {
        return roleInTeam;
    }

    public void setRoleInTeam(String roleInTeam) {
        this.roleInTeam = roleInTeam;
    }
}
