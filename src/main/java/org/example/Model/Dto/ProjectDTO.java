package org.example.Model.Dto;

import java.time.LocalDate;
import java.util.List;

public class ProjectDTO {
    private String name;
    private String priority;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private Long teamId;
    private List<TechnologyDTO> technologies;

    // Getters et setters

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

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TechnologyDTO> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<TechnologyDTO> technologies) {
        this.technologies = technologies;
    }
}
