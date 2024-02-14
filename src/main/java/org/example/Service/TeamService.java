package org.example.Service;

import org.example.Model.*;
import org.example.Model.Dto.TeamMemberDto;
import org.example.Repository.*;

import java.util.ArrayList;
import java.util.List;

public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public TeamService(TeamRepository teamRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    public Team findTeamById(Long teamId) {
        Team team= teamRepository.findTeamById(teamId);
        if(team!=null){
            return team;
        }
        else {
            throw new IllegalArgumentException("Cette équipe n'existe pas");
        }
    }

    public List<TeamMember> addMembersToTeam(TeamMemberDto teamMembersDto, Long idTeam) {
        List<TeamMember> savedTeamMembers = new ArrayList<>();

        Team team = teamRepository.findTeamById(idTeam);
        if(team==null){
            throw new IllegalArgumentException("Équipe non trouvée avec l'ID : " + idTeam);
        }

        for (TeamMemberDto.MemberIdentifier memberId : teamMembersDto.getMembers()) {
            User user = userRepository.findByNameAndEmail(memberId.getName(), memberId.getEmail());
            if (user == null) {
                throw new IllegalArgumentException("Utilisateur non trouvé avec name: "
                        + memberId.getName() + " et email: " + memberId.getEmail());
            }
        }

        for (TeamMemberDto.MemberIdentifier memberId : teamMembersDto.getMembers()) {
            User user = userRepository.findByNameAndEmail(memberId.getName(), memberId.getEmail()) ;



            List<User> members=teamRepository.getMembersByTeamId(idTeam);

            boolean userNotPresent = members.stream()
                    .noneMatch(member -> user.getUserIdentifiant().getEmail().equals(member.getUserIdentifiant().getEmail()));

            if (userNotPresent) {
                TeamMember teamMember = new TeamMember();
                teamMember.setUserIdentity(user);
                teamMember.setTeamId(team);
                TeamMember savedTeamMember = teamRepository.addTeamMember(teamMember);

                if (savedTeamMember != null) {
                    savedTeamMembers.add(savedTeamMember);
                }
            }
        }
        return savedTeamMembers;
    }




    public Team createTeam(String status, String name) {
        Team team = new Team();
        team.setStatus(status);
        team.setName(name);
        return teamRepository.saveTeam(team);
    }

    public List<Team> getAllTeams() {
        return teamRepository.getAllTeams();
    }

    /*

    private boolean isValidTeamSize(Project project, List<User> devs) {
        // Implémenter la logique de vérification de la taille de l'équipe
    }

    private boolean hasRequiredSkills(Project project, List<User> devs) {
        // Implémenter la logique de vérification des compétences
    }*/

}
