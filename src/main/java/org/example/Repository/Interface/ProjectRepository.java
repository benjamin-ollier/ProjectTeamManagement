package org.example.Repository.Interface;


import org.example.Model.Project;
import org.example.Model.User;

import java.util.List;

public interface ProjectRepository {
    Project create(Project project);
    List<Project> getProjectInProgress();
    List<Project> getProjectFinished();
    List<Project> getProjectPlanned();
    Project getProjectByName(String projectName);
    boolean existsProjectByName(String projectName);
    Project updateProject(String projectName, Project updatedProjectInfo);
    Project getProjectbyTeamid(Long teamId);
    List<String> getRequiredSkills(Project project);
    List<Project> getUserProjects(User userIdentity);
}
