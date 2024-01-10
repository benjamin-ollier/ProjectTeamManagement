package org.example;

import org.example.Repository.UserRepository;
import io.javalin.Javalin;
import org.example.Controller.UserController;
import org.example.Service.UserService;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        UserRepository userRepository = new UserRepository(sessionFactory);
        UserService userService = new UserService(userRepository);
        UserController userController = new UserController(userService);


        Javalin app = Javalin.create().start(7070);

        userController.registerRoutes(app);
        //projectController.registerRoutes(app);

        app.error(404, ctx -> ctx.result("Resource not found"));
        app.error(500, ctx -> ctx.result("Internal server error"));

    }
}