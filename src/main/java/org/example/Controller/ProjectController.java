package org.example.Controller;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import org.example.Model.DevSkill;
import org.example.Model.Dto.ProjectDTO;
import org.example.Model.Project;
import org.example.Model.ProjectTechnology;
import org.example.Model.User;
import org.example.Service.DevSkillService;
import org.example.Service.ProjectService;

import java.util.List;

public class ProjectController {
    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    public void registerRoutes(Javalin app) {
        app.post("/project/create", createProject);
        app.get("/project/getAllInProgress", getProjectInProgress);
        app.get("/project/getAllPlanned", getProjectPlanned);
        app.get("/project/getAllFinished", getProjectFinished);
        app.get("/project/getTechnologysByProject/{projectName}", getProjectTechnologys);
        app.put("/project/start/{projectName}", startProject);
        app.put("/project/finish/{projectName}", finishProject);
        app.put("/project/cancel/{projectName}", cancelProject);
    }

    public Handler createProject = ctx -> {
        ProjectDTO projectDTO = ctx.bodyAsClass(ProjectDTO.class);

        Project newProject = projectService.createProject(projectDTO);
        ctx.json(newProject);
    };

    public Handler getProjectInProgress = ctx -> {
        List<Project> projects = projectService.getProjectInProgress();
        ctx.json(projects);
    };

    public Handler getProjectPlanned = ctx -> {
        List<Project> projects = projectService.getProjectPlanned();
        ctx.json(projects);
    };

    public Handler getProjectFinished = ctx -> {
        List<Project> projects = projectService.getProjectFinished();
        ctx.json(projects);
    };

    public Handler getProjectTechnologys = ctx -> {
        String projectName = ctx.pathParam("projectName");
        List<ProjectTechnology> projectTechnologies = projectService.getProjectTechnologys(projectName);
        ctx.json(projectTechnologies);
    };

    public Handler startProject = ctx -> {
        String projectName = ctx.pathParam("projectName");
        Project project = projectService.startProject(projectName);
        ctx.json(project);
    };

    public Handler finishProject = ctx -> {
        String projectName = ctx.pathParam("projectName");
        Project project = projectService.finishProject(projectName);
        ctx.json(project);
    };

    public Handler cancelProject = ctx -> {
        String projectName = ctx.pathParam("projectName");
        Project project = projectService.cancelProject(projectName);
        ctx.json(project);
    };

}
