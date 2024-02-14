package org.example.Controller;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import org.example.Model.Dto.AvailableDev;
import org.example.Model.Dto.TeamMemberDto;
import org.example.Model.Team;
import org.example.Model.TeamMember;
import org.example.Service.ProjectService;
import org.example.Service.TeamService;

import java.util.List;

public class TeamController {
    private TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    public void registerRoutes(Javalin app) {
        app.post("/team/addMember/{idTeam}", addMemberToTeam);
        app.post("/team/createTeam/{name}/{status}", createTeam);
        app.get("/team/getAllTeams",getAllTeams);
    }

    public Handler addMemberToTeam = ctx -> {
        Long idTeam = Long.valueOf(ctx.pathParam("idTeam"));
        TeamMemberDto teamMember = ctx.bodyAsClass(TeamMemberDto.class);

        List<TeamMember> teamMemberJson = teamService.addMembersToTeam(teamMember,idTeam);
        ctx.json(teamMemberJson);
    };

    public Handler createTeam = ctx -> {
        String name = ctx.pathParam("name");
        String status = ctx.pathParam("status");

        ctx.json(teamService.createTeam(status,name));
    };

    public Handler getAllTeams = ctx -> {
        ctx.json(teamService.getAllTeams());
    };


}
