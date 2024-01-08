package org.example.Model;

import java.time.LocalDate;

public class Project {
    private int projectId;
    private String name;
    private String priority; // Valeurs possibles : "normale", "best effort", "critique"
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status; // Valeurs possibles : "en attente", "en cours", "terminé", "annulé"

    public Project() {
    }

    public Project(int projectId, String name, String priority, String description,
                   LocalDate startDate, LocalDate endDate, String status) {
        this.projectId = projectId;
        this.name = name;
        this.priority = priority;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    // Getters et setters pour tous les attributs
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
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
}
