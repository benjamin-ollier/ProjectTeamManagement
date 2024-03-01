package org.example.Service;

import org.example.Model.*;
import org.example.Model.Dto.ProjectDTO;
import org.example.Model.Dto.TechnologyDTO;
import org.example.Repository.*;
import org.example.Service.TeamService.TeamService;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class ProjectService {
    private final JpaProjectRepository jpaProjectRepository;
    private final JpaTechnologyRepository jpaTechnologyRepository;
    private final JpaProjectTechnologyRepository jpaProjectTechnologyRepository;
    private final TeamService teamService;


    public ProjectService(JpaProjectRepository jpaProjectRepository, JpaProjectTechnologyRepository jpaProjectTechnologyRepository, JpaTechnologyRepository jpaTechnologyRepository, TeamService teamService) {
        this.jpaProjectRepository = jpaProjectRepository;
        this.jpaProjectTechnologyRepository = jpaProjectTechnologyRepository;
        this.jpaTechnologyRepository = jpaTechnologyRepository;
        this.teamService = teamService;
    }


    public Project createProject(ProjectDTO projectDTO) {
        if (jpaProjectRepository.existsProjectByName(projectDTO.getName())) {
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
        
        jpaProjectRepository.create(project);

        return project;
    }


    public Technology getOrCreateTechnology(String technology) {
        Technology newTechnology = new Technology();
        newTechnology.setName(technology);

        if (!jpaTechnologyRepository.technologieExists(technology)) {
            jpaTechnologyRepository.addTechnology(newTechnology);
        } else {
            newTechnology = jpaTechnologyRepository.getTechnologyByName(technology);
        }

        return newTechnology;
    }

    public List<Project> getProjectInProgress() {
        return jpaProjectRepository.getProjectInProgress();
    }

    public List<Project> getProjectFinished() {
        return jpaProjectRepository.getProjectFinished();
    }

    public List<Project> getProjectPlanned() {
        return jpaProjectRepository.getProjectPlanned();
    }

    public List<Project> getProject() {
        return jpaProjectRepository.getProjectInProgress();
    }

    public List<ProjectTechnology> getProjectTechnologys(String projectName) {
        return jpaProjectTechnologyRepository.getTechnologyByProjectName(projectName);
    }

    public Project startProject(String projectName) {
        Project project = getProjectByName(projectName);
        if (project == null) {
            throw new IllegalArgumentException("Projet non trouvé avec le nom : " + projectName);
        }
        if (!"planned".equals(project.getStatus())) {
            throw new IllegalArgumentException("Le projet n'est pas dans un état pouvant être démarré.");
        }
        if (project != null && "planned".equals(project.getStatus())) {
            project.setStatus("inprogress");
            project.setStartDate(LocalDate.now());
        }
        return jpaProjectRepository.updateProject(projectName,project);
    }

    public Project finishProject(String projectName) {
        Project project = getProjectByName(projectName);
        if (project == null) {
            throw new IllegalArgumentException("Projet non trouvé avec le nom : " + projectName);
        }
        if (!"inprogress".equals(project.getStatus())) {
            throw new IllegalArgumentException("Seul un projet en cours peut être marqué comme terminé.");
        }
        if (project != null && "inprogress".equals(project.getStatus())) {
            project.setStatus("finished");
            project.setEndDate(LocalDate.now());
        }
        return jpaProjectRepository.updateProject(projectName,project);
    }

    public Project cancelProject(String projectName) {
        Project project = getProjectByName(projectName);
        if (project == null) {
            throw new IllegalArgumentException("Projet non trouvé avec le nom : " + projectName);
        }
        if ("finished".equals(project.getStatus())) {
            throw new IllegalArgumentException("Un projet terminé ne peut pas être annulé.");
        }
        if ("inprogress".equals(project.getStatus())) {
            throw new IllegalArgumentException("Un projet en cours ne peut pas être annulé.");
        }
        if (project != null && !"finished".equals(project.getStatus())) {
            project.setStatus("cancel");
        }
        return jpaProjectRepository.updateProject(projectName,project);
    }

    public Project getProjectByName(String name){
        return jpaProjectRepository.getProjectByName(name);
    }

    public Project determineNextProject() {
        List<Project> pendingProjects = jpaProjectRepository.getProjectPlanned();
        if (pendingProjects.isEmpty()) {
            throw new IllegalArgumentException("Aucun projet en attente.");
        }

        return pendingProjects.stream()
                .sorted(Comparator
                        .comparingInt((Project p) -> mapPriorityToInt(p.getPriority()))
                        .thenComparing(Project::getStartDate)
                        .thenComparing(Project::getEndDate))
                .findFirst()
                .get();
    }

    private int mapPriorityToInt(String priority) {
        switch (priority) {
            case "critique":
                return 3;
            case "best effort":
                return 2;
            case "normale":
            default:
                return 1;
        }
    }

}
