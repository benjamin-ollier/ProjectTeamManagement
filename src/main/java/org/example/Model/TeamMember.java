package org.example.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "teamMember")
public class TeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "teamId", referencedColumnName = "teamId")
    private Team teamId;
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
    @Column(name = "roleInTeam")
    private String roleInTeam;

    public TeamMember(Long id, Team teamId, User user, String roleInTeam) {
        this.id = id;
        this.teamId = teamId;
        this.user = user;
        this.roleInTeam = roleInTeam;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeamId() {
        return teamId;
    }

    public void setTeamId(Team teamId) {
        this.teamId = teamId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRoleInTeam() {
        return roleInTeam;
    }

    public void setRoleInTeam(String roleInTeam) {
        this.roleInTeam = roleInTeam;
    }
}
