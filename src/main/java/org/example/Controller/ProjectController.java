package org.example.Controller;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import org.example.Model.DevSkill;
import org.example.Model.Project;
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
    }

    public Handler createProject = ctx -> {
        Project projectInformation = ctx.bodyAsClass(Project.class);

        Project newProject = projectService.createProject(projectInformation);
        ctx.json(newProject);
    };

    public Handler getProjectInProgress = ctx -> {
        List<Project> projects = projectService.getProjectInProgress();
        ctx.json(projects);
    };

}
