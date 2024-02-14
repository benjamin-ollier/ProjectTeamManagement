package org.example.Model;
import jakarta.persistence.*;

@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;

    public Team(String name, String status) {
        this.name = name;
        this.status = status;
    }

    public Team() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return teamId;
    }

    public void setId(Long teamId) {
        this.teamId = teamId;
    }
}
