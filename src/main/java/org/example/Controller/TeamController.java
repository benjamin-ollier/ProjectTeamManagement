package org.example.Controller;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import org.example.Model.Dto.TeamMemberDto;
import org.example.Model.TeamMember;
import org.example.Service.TeamService.TeamService;

import java.util.List;

public class TeamController {
    private TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    public void registerRoutes(Javalin app) {
        app.post("/team/addMember", addMemberToTeam);
        app.post("/team/createTeam/{name}/{status}/{projectName}", createTeam);
        app.get("/team/getAllTeams",getAllTeams);
        app.get("/team/getAllTeamMember/{idTeam}",getAllTeamMember);
        app.post("/team/transfertDevelopper/{nameOrEmail}/{teamIdDestination}",transfertDevelopper);

    }

    public Handler addMemberToTeam = ctx -> {
        TeamMemberDto teamMember = ctx.bodyAsClass(TeamMemberDto.class);
        List<TeamMember> teamMemberJson = teamService.addMembersToTeam(teamMember);
        ctx.json(teamMemberJson);
    };

    public Handler createTeam = ctx -> {
        String name = ctx.pathParam("name");
        String status = ctx.pathParam("status");
        String projectName = ctx.pathParam("projectName");

        ctx.json(teamService.createTeam(status,name,projectName));
    };

    public Handler getAllTeams = ctx -> {
        ctx.json(teamService.getAllTeams());
    };

    public Handler getAllTeamMember = ctx -> {
        Long idTeam = Long.valueOf(ctx.pathParam("idTeam"));
        ctx.json(teamService.getAllTeamMember(idTeam));
    };

    public Handler transfertDevelopper = ctx -> {
        Long teamIdDestination = Long.valueOf(ctx.pathParam("teamIdDestination"));
        String nameOrEmailUser = ctx.pathParam("nameOrEmail");

        ctx.json(teamService.transferDeveloper(nameOrEmailUser,teamIdDestination));
    };


}
