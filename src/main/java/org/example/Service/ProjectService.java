package org.example.Service;

import org.example.Model.*;
import org.example.Model.Dto.ProjectDTO;
import org.example.Model.Dto.TechnologyDTO;
import org.example.Repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TechnologyRepository technologyRepository;
    private final ProjectTechnologyRepository projectTechnologyRepository;
    private final TeamService teamService;


    public ProjectService(ProjectRepository projectRepository, ProjectTechnologyRepository projectTechnologyRepository, TechnologyRepository technologyRepository, TeamService teamService) {
        this.projectRepository = projectRepository;
        this.projectTechnologyRepository = projectTechnologyRepository;
        this.technologyRepository = technologyRepository;
        this.teamService = teamService;
    }


    public Project createProject(ProjectDTO projectDTO) {
        if (projectRepository.existsProjectByName(projectDTO.getName())) {
            throw new IllegalArgumentException("Ce nom de projet existe déjà");
        }

        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setPriority(projectDTO.getPriority());
        project.setDescription(projectDTO.getDescription());
        project.setStartDate(projectDTO.getStartDate());
        project.setEndDate(projectDTO.getEndDate());
        project.setStatus("planned");

        if (projectDTO.getTeamId() != null) {
            Team team = teamService.findTeamById(projectDTO.getTeamId());
            project.setTeamId(team);
        }


        for (TechnologyDTO techDTO : projectDTO.getTechnologies()) {
            ProjectTechnology projectTechnology = new ProjectTechnology();
            projectTechnology.setDevCount(techDTO.getDevCount());

            Technology techno = getOrCreateTechnology(techDTO.getTechName());
            projectTechnology.setTechId(techno);

            project.addTechnology(projectTechnology);
        }
        
        projectRepository.create(project);

        return project;
    }


    private Technology getOrCreateTechnology(String technology) {
        Technology newTechnology = new Technology();
        newTechnology.setName(technology);

        if (!technologyRepository.technologieExists(technology)) {
            technologyRepository.addTechnology(newTechnology);
        } else {
            newTechnology = technologyRepository.getTechnologyByName(technology);
        }

        return newTechnology;
    }

    public List<Project> getProjectInProgress() {
        return projectRepository.getProjectInProgress();
    }

    public List<Project> getProjectFinished() {
        return projectRepository.getProjectFinished();
    }

    public List<Project> getProjectPlanned() {
        return projectRepository.getProjectPlanned();
    }

    public List<Project> getProject() {
        return projectRepository.getProjectInProgress();
    }

    public List<ProjectTechnology> getProjectTechnologys(String projectName) {
        return projectTechnologyRepository.getTechnologyByProjectName(projectName);
    }

    public Project startProject(String projectName) {
        Project project = projectRepository.getProjectByName(projectName);
        if (project != null && "en attente".equals(project.getStatus())) {
            project.setStatus("en cours");
            project.setStartDate(LocalDate.now());
        }
        return project;
    }

    public Project finishProject(String projectName) {
        Project project = projectRepository.getProjectByName(projectName);
        if (project != null && "en cours".equals(project.getStatus())) {
            project.setStatus("terminé");
            project.setEndDate(LocalDate.now());
        }
        return project;
    }

    public Project cancelProject(String projectName) {
        Project project = projectRepository.getProjectByName(projectName);
        if (project != null && !"terminé".equals(project.getStatus())) {
            project.setStatus("annulé");
        }
        return project;
    }
}
