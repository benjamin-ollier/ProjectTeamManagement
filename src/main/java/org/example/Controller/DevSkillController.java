package org.example.Controller;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import org.example.Model.DevSkill;
import org.example.Service.DevSkillService;

import java.util.List;

public class DevSkillController {
    private DevSkillService devSkillService;

    public DevSkillController(DevSkillService devSkillService) {
        this.devSkillService = devSkillService;
    }

    public void registerRoutes(Javalin app) {
        app.get("/skills/skills/{identity}", getUserSkills);
        app.post("/skill/addOrUpdateSkill/{identity}/{technology}/{yearsOfExperience}", addOrUpdateSkill);
        app.get("/skill/searchByTechnoAndLevel/{technology}/{level}", getUserByTechnoAndLevel);

    }

    public Handler getUserSkills = ctx -> {
        String identity = ctx.pathParam("identity");
        List<DevSkill> usersWithSkill = devSkillService.getUserSkills(identity);
        ctx.json(usersWithSkill);
    };

    public Handler addOrUpdateSkill = ctx -> {
        String identity = ctx.pathParam("identity");
        String technology = ctx.pathParam("technology");
        int yearsOfExperience = Integer.parseInt(ctx.pathParam("yearsOfExperience"));


        DevSkill skill = devSkillService.addOrUpdateSkill(identity, technology, yearsOfExperience);
        ctx.status(201).json(skill);
    };

    public Handler getUserByTechnoAndLevel = ctx -> {
        String level = ctx.pathParam("level");
        String technology = ctx.pathParam("technology");
        List<DevSkill> users = devSkillService.getUserByTechnoAndLevel(level,technology);
        ctx.status(201).json(users);
    };
}