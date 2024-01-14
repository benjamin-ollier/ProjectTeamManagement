package org.example;

import org.example.Controller.DevSkillController;
import org.example.Repository.DevSkillRepository;
import org.example.Repository.TechnologyRepository;
import org.example.Repository.UserRepository;
import io.javalin.Javalin;
import org.example.Controller.UserController;
import org.example.Service.DevSkillService;
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

        DevSkillRepository devSkillRepository = new DevSkillRepository(sessionFactory);
        DevSkillService devSkillService = new DevSkillService(devSkillRepository, technologyRepository, userRepository);
        DevSkillController devSkillController = new DevSkillController(devSkillService);


        Javalin app = Javalin.create().start(7070);

        userController.registerRoutes(app);
        devSkillController.registerRoutes(app);
        //projectController.registerRoutes(app);

        app.error(404, ctx -> ctx.result("Resource not found"));
        app.error(500, ctx -> ctx.result("Internal server error"));
        app.exception(IllegalArgumentException.class, (e, ctx) -> {
            ctx.status(400); // Bad Request
            ctx.json("Error: " + e.getMessage());
        });

    }
}