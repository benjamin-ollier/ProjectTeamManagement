package org.example.Model;

import jakarta.persistence.*;
import org.hibernate.grammars.hql.HqlParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "project")
public class Project {
    @Id
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "teamId", referencedColumnName = "teamId")
    private Team teamId;

    @Column(name = "priority")
    private String priority; // Valeurs possibles : "normale", "best effort", "critique"

    @Column(name = "description")
    private String description;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;

    @Column(name = "status")
    private String status; // Valeurs possibles : "en attente", "en cours", "terminé", "annulé"

    @OneToMany(mappedBy = "projectName", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectTechnology> technologies = new ArrayList<>();

    public Project() {
    }

    public Project(String name, String priority, String description,
                   LocalDate startDate, LocalDate endDate, String status) {
        this.name = name;
        this.priority = priority;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    // Getters et setters pour tous les attributs
/*
    public List<ProjectTechnology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<ProjectTechnology> technologies) {
        this.technologies = technologies;
    }

    // Méthodes pour ajouter et supprimer des technologies
    public void addTechnology(ProjectTechnology projectTechnology) {
        this.technologies.add(projectTechnology);
    }

    public void removeTechnology(ProjectTechnology technology) {
        technologies.remove(technology);
        technology.setProjectName(null);
    }*/

    public void addTechnology(ProjectTechnology technology) {
        technologies.add(technology);
        technology.setProjectName(this);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Team getTeamId() {
        return teamId;
    }

    public void setTeamId(Team teamId) {
        this.teamId = teamId;
    }
}
