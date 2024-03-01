package org.example.Service.TeamService;

import org.example.Model.Project;
import org.example.Model.Team;
import org.example.Model.TeamMember;
import org.example.Model.User;
import org.example.Repository.JpaProjectRepository;
import org.example.Repository.JpaTeamRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TeamValidationService {

    private final JpaProjectRepository jpaProjectRepository;
    private final JpaTeamRepository jpaTeamRepository;

    public TeamValidationService(JpaProjectRepository jpaProjectRepository, JpaTeamRepository jpaTeamRepository) {
        this.jpaProjectRepository = jpaProjectRepository;
        this.jpaTeamRepository = jpaTeamRepository;
    }


    public void validateTeamRules(List<TeamMember> combinedMembers, Project project) {
        int expertCount = 0;
        int juniorCount = 0;
        int totalCount = combinedMembers.size();

        for (TeamMember member : combinedMembers) {
            String level = member.getDevskill().getLevel();
            switch (level) {
                case "EXPERIENCED":
                    expertCount++;
                    break;
                case "JUNIOR":
                    juniorCount++;
                    break;
            }
        }

        validateTeamSize(totalCount);
        validateJuniorAndExpertPresence(juniorCount, expertCount);
        validateJuniorLimit(juniorCount);
        validateExpertConditions(expertCount, totalCount, project);
        validateTeamSkills(combinedMembers, project);
        combinedMembers.forEach(member -> validateTeamChangeFrequency(member.getUserIdentity()));
        validateMemberAvailability(combinedMembers,project);
    }

    public List<TeamMember> validateTeamRulesForTransfer(TeamMember user, Team sourceTeam, Team destinationTeam, Project destinationProject) {
        // membre de l'equipe source sans le developpeur
        List<TeamMember> sourceTeamMembersMinusDeveloper = jpaTeamRepository.getAllTeamMembers(sourceTeam.getTeamId()).stream()
                .filter(member -> !member.getUserIdentity().equals(user.getUserIdentity()))
                .collect(Collectors.toList());

        // copie des membres de l'équipe de destination avec le développeur ajouté
        List<TeamMember> destinationTeamMembersWithDeveloper = new ArrayList<>(jpaTeamRepository.getAllTeamMembers(destinationTeam.getTeamId()));
        destinationTeamMembersWithDeveloper.add(user);

        // validation de l'équipe source sans le développeur
        validateTeamRules(sourceTeamMembersMinusDeveloper, jpaProjectRepository.getProjectbyTeamid(sourceTeam.getTeamId()));

        // validation de l'équipe de destination avec le développeur ajouté
        validateTeamRules(destinationTeamMembersWithDeveloper, destinationProject);

        return destinationTeamMembersWithDeveloper;
    }



    private void validateTeamSize(int totalCount) {
        if (totalCount < 3 || totalCount > 8) {
            throw new IllegalArgumentException("L'équipe doit être constituée d'au moins 3 et de 8 membres au maximum.");
        }
    }

    private void validateJuniorAndExpertPresence(int juniorCount, int expertCount) {
        if (juniorCount > 0 && expertCount < 1) {
            throw new IllegalArgumentException("Un junior nécessite la présence d'au moins un expert dans l'équipe.");
        }
    }

    private void validateJuniorLimit(int juniorCount) {
        if (juniorCount > 3) {
            throw new IllegalArgumentException("Il ne peut y avoir plus de 3 juniors dans une même équipe.");
        }
    }

    private void validateExpertConditions(int expertCount, int totalCount, Project project) {
        if (expertCount > 0 && totalCount < 5 && !"critique".equalsIgnoreCase(project.getPriority())) {
            throw new IllegalArgumentException("Un expert ne peut être dans une équipe de moins de 5 membres, sauf pour un projet critique.");
        }

        if (expertCount == 0) {
            validateIntermediateDurationWithoutExpert(project);
        }
    }

    private void validateIntermediateDurationWithoutExpert(Project project) {
        LocalDate startDate = project.getStartDate();
        LocalDate endDate = project.getEndDate();
        long monthsBetween = ChronoUnit.MONTHS.between(startDate, endDate);
        if (monthsBetween > 6) {
            throw new IllegalArgumentException("Un projet de plus de 6 mois doit inclure au moins un expert.");
        }
    }

    private void validateTeamChangeFrequency(User dev) {
        LocalDate lastTeamChange = dev.getLastTeamChangeDate();
        if (lastTeamChange != null && ChronoUnit.MONTHS.between(lastTeamChange, LocalDate.now()) < 6) {
            throw new IllegalArgumentException("Le développeur " + dev.getUserIdentifiant().getName() + " a changé d'équipe il y a moins de 6 mois.");
        }
    }

    private void validateTeamSkills(List<TeamMember> teamMembers, Project project) {
        List<String> requiredSkills = jpaProjectRepository.getRequiredSkills(project);
        for (String skill : requiredSkills) {
            boolean skillCovered = teamMembers.stream().anyMatch(member -> member.getDevskill().getTechId().getName().equals(skill));
            if (!skillCovered) {
                throw new IllegalArgumentException("La compétence requise " + skill + " n'est pas couverte par l'équipe.");
            }
        }
    }

    private void validateMemberAvailability(List<TeamMember> teamMembers, Project project) {
        for (TeamMember member : teamMembers) {
            // get projets auxquels le membre est actuellement affecté
            List<Project> currentProjects = jpaProjectRepository.getUserProjects(member.getUserIdentity())
                    .stream()
                    .filter(p -> !p.getName().equals(project.getName()))
                    .toList();
            for (Project currentProject : currentProjects) {
                if (projectsSameDate(project, currentProject)) {
                    throw new IllegalArgumentException("Le membre de l'équipe " + member.getUserIdentity().getUserIdentifiant().getName() + " est déjà engagé dans le projet " + currentProject.getName() + " pendant la durée du projet " + project.getName());
                }
            }
        }
    }

    private boolean projectsSameDate(Project project1, Project project2) {
        return !(project1.getEndDate().isBefore(project2.getStartDate()) || project1.getStartDate().isAfter(project2.getEndDate()));
    }
}