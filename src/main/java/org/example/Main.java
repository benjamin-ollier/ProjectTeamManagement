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


        app.get("/users", userController.getAllUsers);
        //app.get("/users/:id", userController.getUserById);
        //app.post("/users", userController.createUser);
    }
}