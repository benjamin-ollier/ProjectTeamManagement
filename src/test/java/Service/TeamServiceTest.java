package Service;

import org.example.Model.*;
import org.example.Model.Dto.DeveloperProfileDTO;
import org.example.Model.Dto.TeamMemberDto;
import org.example.Repository.Interface.DevSkillRepository;
import org.example.Repository.JpaDevSkillRepository;
import org.example.Repository.JpaProjectRepository;
import org.example.Repository.JpaTeamRepository;
import org.example.Repository.JpaUserRepository;
import org.example.Service.TeamService.TeamService;
import org.example.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {
    @Mock
    private JpaTeamRepository jpaTeamRepository;
    @Mock
    private JpaUserRepository jpaUserRepository;
    @Mock
    private JpaProjectRepository jpaProjectRepository;
    @Mock
    private DevSkillRepository devSkillRepository;

    @InjectMocks
    private TeamService teamService;

    private Team team;
    private User user;
    private Project project;
    private DevSkill devSkill;
    private TeamMember teamMember;

    @BeforeEach
    void setUp() {
        // Initialize your test objects here with dummy data
        team = new Team("Team A", "active");
        team.setId(1L);
        user = new User(new UserId("test@example.com", "Test User"), "Developer", LocalDate.now());
        project = new Project("Project X", "normale", "A test project", LocalDate.now(), LocalDate.now().plusMonths(2), "en attente");
        devSkill = new DevSkill(1L, team, user, new Technology(), 5);
        teamMember = new TeamMember(1L, user, team, devSkill);

        lenient().when(jpaTeamRepository.findTeamById(1L)).thenReturn(team);
        lenient().when(jpaUserRepository.findByNameAndEmail("Test User", "test@example.com")).thenReturn(user);
        lenient().when(devSkillRepository.findDevSkillById(1L)).thenReturn(devSkill);
        lenient().when(jpaProjectRepository.getProjectbyTeamid(1L)).thenReturn(project);
    }

    @Test
    void findTeamById_WhenTeamExists_ReturnsTeam() {
        Team result = teamService.findTeamById(1L);
        assertNotNull(result);
        assertEquals("Team A", result.getName());
    }

    @Test
    void findTeamById_WhenTeamDoesNotExist_ThrowsException() {
        when(jpaTeamRepository.findTeamById(anyLong())).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> teamService.findTeamById(99L));
    }

    @Test
    void createTeam_WhenTeamDoesNotAlreadyExist() {
        String name = "New Team";
        String status = "active";
        String projectName = "Project X";

        when(jpaTeamRepository.saveTeam(any(Team.class))).thenReturn(new Team(name, status));
        when(jpaProjectRepository.getProjectByName(projectName)).thenReturn(project);

        Team createdTeam = teamService.createTeam(status, name, projectName);

        assertNotNull(createdTeam);
        assertEquals(name, createdTeam.getName());
    }

    @Test
    void createTeam_WhenTeamNameAlreadyExists() {
        when(jpaTeamRepository.getAllTeams()).thenReturn(Arrays.asList(team));

        assertThrows(IllegalArgumentException.class, () -> {
            teamService.createTeam("active", "Team A", "Project X");
        });
    }

}
