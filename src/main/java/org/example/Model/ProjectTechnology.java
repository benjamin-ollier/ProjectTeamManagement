package org.example.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "projectTechnologie")
public class ProjectTechnology {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "name")
    private Project projectName;
    @ManyToOne
    @JoinColumn(name = "techId", referencedColumnName = "techId")
    private Technology techId;

    @Column(name = "devCount")
    private int devCount;

    public ProjectTechnology(int id, Project projectName, Technology techId) {
        this.id = id;
        this.projectName = projectName;
        this.techId = techId;
    }

    public ProjectTechnology() {

    }


    public int getDevCount() {
        return devCount;
    }

    public void setDevCount(int devCount) {
        this.devCount = devCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Project getProjectName() {
        return projectName;
    }

    public void setProjectName(Project projectName) {
        this.projectName = projectName;
    }

    public Technology getTechId() {
        return techId;
    }

    public void setTechId(Technology techId) {
        this.techId = techId;
    }
}
