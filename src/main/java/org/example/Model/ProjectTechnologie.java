package org.example.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "projectTechnologie")
public class ProjectTechnologie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "projectId", referencedColumnName = "projectId")
    private Project projectId;
    @ManyToOne
    @JoinColumn(name = "techId", referencedColumnName = "techId")
    private Technologie techId;

    public ProjectTechnologie(int id, Project projectId, Technologie techId) {
        this.id = id;
        this.projectId = projectId;
        this.techId = techId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Project getProjectId() {
        return projectId;
    }

    public void setProjectId(Project projectId) {
        this.projectId = projectId;
    }

    public Technologie getTechId() {
        return techId;
    }

    public void setTechId(Technologie techId) {
        this.techId = techId;
    }
}
