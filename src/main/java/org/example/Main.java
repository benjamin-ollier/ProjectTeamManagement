package org.example;

import org.example.Controller.DevSkillController;
import org.example.Controller.ProjectController;
import org.example.Controller.TeamController;
import org.example.Model.Team;
import org.example.Repository.*;
import io.javalin.Javalin;
import org.example.Controller.UserController;
import org.example.Repository.Interface.DevSkillRepository;
import org.example.Service.DevSkillService;
import org.example.Service.ProjectService;
import org.example.Service.TeamService;
import org.example.Service.UserService;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        UserRepository userRepository = new UserRepository(sessionFactory);
        UserService userService = new UserService(userRepository);
        UserController userController = new UserController(userService);

        TechnologyRepository technologyRepository = new TechnologyRepository(sessionFactory);

        DevSkillRepository devSkillRepository = new JpaDevSkillRepository(sessionFactory);
        DevSkillService devSkillService = new DevSkillService(devSkillRepository, technologyRepository, userRepository);
        DevSkillController devSkillController = new DevSkillController(devSkillService);

        TeamRepository teamRepository = new TeamRepository(sessionFactory);
        TeamService teamService =  new TeamService(teamRepository, userRepository);
        TeamController teamController = new TeamController(teamService);

        ProjectRepository projectRepository = new ProjectRepository((sessionFactory));
        ProjectTechnologyRepository projectTechnologyRepository = new ProjectTechnologyRepository(sessionFactory);
        ProjectService projectService = new ProjectService(projectRepository,projectTechnologyRepository,technologyRepository,teamService);
        ProjectController projectController = new ProjectController(projectService);


        Javalin app = Javalin.create().start(7070);

        userController.registerRoutes(app);
        devSkillController.registerRoutes(app);
        projectController.registerRoutes(app);
        teamController.registerRoutes(app);

        app.error(404, ctx -> ctx.result("Resource not found"));
        app.error(500, ctx -> ctx.result("Internal server error"));
        app.exception(IllegalArgumentException.class, (e, ctx) -> {
            ctx.status(400); // Bad Request
            ctx.json("Error: " + e.getMessage());
        });

    }
}