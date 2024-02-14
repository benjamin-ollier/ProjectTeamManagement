package org.example.Controller;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import org.example.Model.DevSkill;
import org.example.Model.Dto.AvailableDev;
import org.example.Model.Technology;
import org.example.Model.User;
import org.example.Service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void registerRoutes(Javalin app) {
        app.post("/user/create", createUser);
        app.get("/user/name/{name}", getUserByName);
        app.get("/user/email/{email}", getUserByEmail);
        app.get("/users", getAllUsers);
        app.get("/users/findAvailableDevelopers", findAvailableDevelopers);
        app.put("/user/update/{identity}", updateUser);
        app.delete("/user/delete/{name}/{email}", deleteUser);

    }

    public Handler getAllUsers = ctx -> {
        ctx.json(userService.getAllUsers());
    };
    public Handler getUserByName = ctx -> {
        String name = ctx.pathParam("name");
        User user = userService.getUserByName(name);
        if (user != null) {
            ctx.json(user);
        } else {
            ctx.status(404).result("User not found");
        }
    };

    public Handler getUserByEmail = ctx -> {
        String email = ctx.pathParam("email");
        User user = userService.getUserByEmail(email);
        if (user != null) {
            ctx.json(user);
        } else {
            ctx.status(404).result("User not found");
        }
    };

    public Handler findAvailableDevelopers = ctx -> {
        AvailableDev competence = ctx.bodyAsClass(AvailableDev.class);
        List<User> user = userService.getAllAvailableDevelopers(competence);
        if (!user.isEmpty()) {
            ctx.json(user);
        } else {
            ctx.status(404).result("Users not found");
        }
    };


    public Handler createUser = ctx -> {
        User user = ctx.bodyAsClass(User.class);
        User createdUser = userService.createUser(user);
        ctx.status(201).json(createdUser);
    };


    public Handler updateUser = ctx -> {
        String identity = ctx.pathParam("identity");
        User userToUpdate = ctx.bodyAsClass(User.class);
        User updatedUser;

        updatedUser = userService.updateUser(identity, userToUpdate);

        if (updatedUser != null) {
            ctx.status(200).json(updatedUser);
        } else {
            ctx.status(404).result("User not found");
        }
    };

    public Handler deleteUser = ctx -> {
        String name = ctx.pathParam("name");
        String email = ctx.pathParam("email");
        boolean deleted = userService.deleteUser(name, email);
        if (deleted) {
            ctx.status(200).json(Map.of("message", "User has been deleted"));
        } else {
            ctx.status(404).result("User not found");
        }
    };

}