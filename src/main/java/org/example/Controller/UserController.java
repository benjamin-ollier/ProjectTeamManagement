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
}
