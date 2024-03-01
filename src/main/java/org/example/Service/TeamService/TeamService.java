package org.example.Service.TeamService;

import org.example.Model.*;
import org.example.Model.Dto.TeamMemberDto;
import org.example.Repository.*;
import org.example.Repository.Interface.DevSkillRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public class TeamService {

    private final JpaTeamRepository jpaTeamRepository;
    private final JpaUserRepository jpaUserRepository;

    private final JpaProjectRepository jpaProjectRepository;

    private final DevSkillRepository devSkillRepository;

    public TeamService(JpaTeamRepository jpaTeamRepository, JpaUserRepository jpaUserRepository, DevSkillRepository devSkillRepository, JpaProjectRepository jpaProjectRepository) {
        this.jpaTeamRepository = jpaTeamRepository;
        this.jpaUserRepository = jpaUserRepository;
        this.devSkillRepository = devSkillRepository;
        this.jpaProjectRepository = jpaProjectRepository;
    }

    public Team findTeamById(Long teamId) {
        Team team= jpaTeamRepository.findTeamById(teamId);
        if(team!=null){
            return team;
        }
        else {
            throw new IllegalArgumentException("Cette équipe n'existe pas");
        }
    }

    public List<TeamMember> addMembersToTeam(TeamMemberDto teamMembersDto) {
        List<TeamMember> potentialNewMembers = new ArrayList<>();
        List<TeamMember> savedTeamMembers = new ArrayList<>();

        Team team = jpaTeamRepository.findTeamById(teamMembersDto.getTeamId());
        if (team == null) {
            throw new IllegalArgumentException("Équipe non trouvée avec l'ID : " + teamMembersDto.getTeamId());
        }

        List<TeamMember> existingMembers = jpaTeamRepository.getAllTeamMembers(team.getTeamId());

        // Préparation de la liste des membres potentiels pour la validation
        for (TeamMemberDto.MemberIdentifier memberId : teamMembersDto.getMembers()) {
            User user = jpaUserRepository.findByNameAndEmail(memberId.getName(), memberId.getEmail());
            if (user == null) {
                throw new IllegalArgumentException("Utilisateur non trouvé avec name: " + memberId.getName() + " et email: " + memberId.getEmail());
            }

            DevSkill devSkill = devSkillRepository.findDevSkillById(memberId.getDevSkillId());
            if (devSkill == null) {
                throw new IllegalArgumentException("Compétence non trouvée avec l'ID : " + memberId.getDevSkillId());
            }

            TeamMember potentialMember = new TeamMember();
            potentialMember.setUserIdentity(user);
            potentialMember.setDevskill(devSkill);
            potentialMember.setTeamId(team);
            potentialNewMembers.add(potentialMember);
        }

        // Combinaison des membres existants et des nouveaux membres
        List<TeamMember> combinedMembers = new ArrayList<>(existingMembers);
        combinedMembers.addAll(potentialNewMembers);

        Project project = jpaProjectRepository.getProjectbyTeamid(team.getTeamId());
        TeamValidationService teamValidationService = new TeamValidationService(jpaProjectRepository, jpaTeamRepository);
        teamValidationService.validateTeamRules(combinedMembers, project);


        // Ajout des membres après validation réussie
        for (TeamMember member : potentialNewMembers) {
            TeamMember savedTeamMember = jpaTeamRepository.addTeamMember(member);
            if (savedTeamMember != null) {
                savedTeamMembers.add(savedTeamMember);
            }
        }

        return savedTeamMembers;
    }


    public Team createTeam(String status, String name, String projectName) {
        List<Team> existingTeams = jpaTeamRepository.getAllTeams();

        for (Team existingTeam : existingTeams) {
            if (existingTeam.getName().equalsIgnoreCase(name)) {
                throw new IllegalArgumentException("Une équipe avec le nom '" + name + "' existe déjà.");
            }
        }
        Team team = new Team();
        team.setStatus(status);
        team.setName(name);
        Project project = jpaProjectRepository.getProjectByName(projectName);
        team= jpaTeamRepository.saveTeam(team);
        project.setTeamId(team);
        jpaProjectRepository.updateProject(projectName,project);
        return team;
    }

    public List<Team> getAllTeams() {
        return jpaTeamRepository.getAllTeams();
    }

    public List<TeamMember> getAllTeamMember(Long idTeam) {
        return jpaTeamRepository.getAllTeamMembers(idTeam);
    }


    public List<TeamMember> transferDeveloper(String nameOrEmail, Long teamId) {
        TeamMember member = jpaTeamRepository.findTeamMemberByNameOrEmail(nameOrEmail);
        if (member == null) {
            throw new IllegalArgumentException("Aucun membre de l'équipe trouvé avec le nom ou l'email : " + nameOrEmail);
        }

        Team newTeam = jpaTeamRepository.findTeamById(teamId);
        if (newTeam == null) {
            throw new IllegalArgumentException("Équipe non trouvée avec l'ID : " + teamId);
        }

        Project project = jpaProjectRepository.getProjectbyTeamid(teamId);
        if (project == null) {
            throw new IllegalArgumentException("Projet non trouvée associé à la team: " + teamId);
        }
        TeamValidationService teamValidationService = new TeamValidationService(jpaProjectRepository, jpaTeamRepository);
        List <TeamMember> tm=teamValidationService.validateTeamRulesForTransfer(member,member.getTeamId(),newTeam,project);

        User user = member.getUserIdentity();
        user.setLastTeamChangeDate(LocalDate.now());
        jpaUserRepository.update(user);

        member.setTeamId(newTeam);
        jpaTeamRepository.addTeamMember(member);
        jpaTeamRepository.deleteTeamMember(member);

        return tm;
    }


}
