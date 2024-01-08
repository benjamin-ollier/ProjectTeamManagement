package org.example.Controller;

import io.javalin.http.Handler;
import org.example.Service.UserService;

public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public Handler getAllUsers = ctx -> {
        ctx.json(userService.getAllUsers());
    };
/*
    public Handler getUserById = ctx -> {
        String id = ctx.pathParam("id");
        ctx.json(userService.getUserById(id));
    };

    public Handler createUser = ctx -> {
        User user = ctx.bodyAsClass(User.class);
        userService.createUser(user);
        ctx.status(201).json(user);
    };*/

}
