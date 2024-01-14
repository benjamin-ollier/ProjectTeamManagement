package org.example.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "projectTechnologie")
public class ProjectTechnology {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "projectId", referencedColumnName = "projectId")
    private Project projectId;
    @ManyToOne
    @JoinColumn(name = "techId", referencedColumnName = "techId")
    private Technology techId;

    public ProjectTechnology(int id, Project projectId, Technology techId) {
        this.id = id;
        this.projectId = projectId;
        this.techId = techId;
    }

    public ProjectTechnology() {

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

    public Technology getTechId() {
        return techId;
    }

    public void setTechId(Technology techId) {
        this.techId = techId;
    }
}
