package org.example.Service;

import org.example.Model.DevSkill;
import org.example.Model.Project;
import org.example.Model.User;
import org.example.Repository.ProjectRepository;
import org.example.Repository.UserRepository;

import java.util.List;

public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    public Project createProject(Project projectInformation) {
        return projectRepository.create(projectInformation);
    }

    public List<Project> getProjectInProgress() {
        return projectRepository.getProjectInProgress();
    }
}
