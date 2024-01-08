package org.example;

import io.javalin.Javalin;
import org.example.Controller.UserController;
import org.example.Service.UserService;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7070);

        UserService userService = new UserService();
        UserController userController = new UserController(userService);

        app.get("/users", userController.getAllUsers);
    }
}