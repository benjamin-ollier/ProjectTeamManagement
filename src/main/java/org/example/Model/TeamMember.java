package org.example.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "teamMember")
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "userIdentityEmail", referencedColumnName = "email"),
            @JoinColumn(name = "userIdentityName", referencedColumnName = "name")
    })
    private User userIdentity;

    @ManyToOne
    @JoinColumn(name = "teamId", referencedColumnName = "teamId")
    private Team teamId;

    public TeamMember(Long id, User userIdentity, Team teamId) {
        this.id = id;
        this.userIdentity = userIdentity;
        this.teamId = teamId;
    }

    public TeamMember(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(User userIdentity) {
        this.userIdentity = userIdentity;
    }

    public Team getTeamId() {
        return teamId;
    }

    public void setTeamId(Team teamId) {
        this.teamId = teamId;
    }
}