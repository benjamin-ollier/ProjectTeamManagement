package org.example;

import org.example.Controller.DevSkillController;
import org.example.Controller.ProjectController;
import org.example.Controller.TeamController;
import org.example.Repository.*;
import io.javalin.Javalin;
import org.example.Controller.UserController;
import org.example.Repository.Interface.DevSkillRepository;
import org.example.Service.DevSkillService;
import org.example.Service.ProjectService;
import org.example.Service.TeamService.TeamService;
import org.example.Service.UserService;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        JpaDevSkillRepository jpaDevSkillRepository = new JpaDevSkillRepository(sessionFactory);
        JpaProjectRepository jpaProjectRepository = new JpaProjectRepository((sessionFactory));
        JpaUserRepository jpaUserRepository = new JpaUserRepository(sessionFactory);
        UserService userService = new UserService(jpaUserRepository, jpaProjectRepository,jpaDevSkillRepository);
        UserController userController = new UserController(userService);

        JpaTechnologyRepository jpaTechnologyRepository = new JpaTechnologyRepository(sessionFactory);

        DevSkillService devSkillService = new DevSkillService(jpaDevSkillRepository, jpaTechnologyRepository, jpaUserRepository);
        DevSkillController devSkillController = new DevSkillController(devSkillService);

        JpaTeamRepository jpaTeamRepository = new JpaTeamRepository(sessionFactory);
        TeamService teamService =  new TeamService(jpaTeamRepository, jpaUserRepository, jpaDevSkillRepository, jpaProjectRepository);
        TeamController teamController = new TeamController(teamService);

        JpaProjectTechnologyRepository jpaProjectTechnologyRepository = new JpaProjectTechnologyRepository(sessionFactory);
        ProjectService projectService = new ProjectService(jpaProjectRepository, jpaProjectTechnologyRepository, jpaTechnologyRepository,teamService);
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