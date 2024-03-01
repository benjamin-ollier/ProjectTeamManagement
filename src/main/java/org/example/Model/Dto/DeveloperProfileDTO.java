package org.example.Model.Dto;

import org.example.Model.DevSkill;
import org.example.Model.Project;
import org.example.Model.User;

import java.util.List;

public class DeveloperProfileDTO {
    private User user;
    private List<DevSkill> devSkills;
    private List<Project> projects;

    // Constructors
    public DeveloperProfileDTO() {
    }

    public DeveloperProfileDTO(User user, List<DevSkill> devSkills, List<Project> projects) {
        this.user = user;
        this.devSkills = devSkills;
        this.projects = projects;
    }

    // Getters and Setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<DevSkill> getDevSkills() {
        return devSkills;
    }

    public void setDevSkills(List<DevSkill> devSkills) {
        this.devSkills = devSkills;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
