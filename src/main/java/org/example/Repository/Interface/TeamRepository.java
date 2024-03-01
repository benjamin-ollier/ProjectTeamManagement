package org.example.Repository.Interface;

import org.example.Model.Team;
import org.example.Model.TeamMember;
import org.example.Model.User;
import java.util.List;

public interface TeamRepository {
    Team findTeamById(Long id);
    TeamMember addTeamMember(TeamMember teamMember);
    boolean deleteTeamMember(TeamMember teamMember);
    Team saveTeam(Team team);
    List<Team> getAllTeams();
    List<User> getUsersByTeamId(Long teamId);
    List<TeamMember> getAllTeamMembers(Long idTeam);
    TeamMember findTeamMemberByNameOrEmail(String nameOrEmail);
}
