package Service;

import org.example.Model.*;
import org.example.Model.Dto.ProjectDTO;
import org.example.Model.Dto.TechnologyDTO;
import org.example.Repository.JpaProjectRepository;
import org.example.Repository.JpaProjectTechnologyRepository;
import org.example.Repository.JpaTechnologyRepository;
import org.example.Service.ProjectService;
import org.example.Service.TeamService.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
    @Mock
    private JpaProjectRepository jpaProjectRepository;
    @Mock
    private JpaTechnologyRepository jpaTechnologyRepository;
    @Mock
    private JpaProjectTechnologyRepository jpaProjectTechnologyRepository;
    @Mock
    private TeamService teamService;

    @InjectMocks
    private ProjectService projectService;

    private ProjectDTO projectDTO;
    private TechnologyDTO technologyDTO;
    private Team team;

    @BeforeEach
    void setUp() {
        technologyDTO = new TechnologyDTO();
        technologyDTO.setTechName("Java");
        technologyDTO.setDevCount(2);

        projectDTO = new ProjectDTO();
        projectDTO.setName("New Project");
        projectDTO.setTechnologies(List.of(technologyDTO));
        projectDTO.setTeamId(1L);

        team = new Team();
        team.setId(1L);

        lenient().when(teamService.findTeamById(anyLong())).thenReturn(team);
        lenient().when(jpaTechnologyRepository.technologieExists(anyString())).thenReturn(false);
        lenient().when(jpaTechnologyRepository.getTechnologyByName(anyString())).thenReturn(new Technology());
    }

    @Nested
    class createProjectTests {
        @Test
        void createProjectWithNewTechnology() {
            projectService.createProject(projectDTO);

            verify(jpaTechnologyRepository).addTechnology(any(Technology.class));
            verify(jpaProjectRepository).create(any(Project.class));
        }

        @Test
        void whenTechnologyAlreadyExists_thenShouldNotCreateNewTechnology() {
            when(jpaTechnologyRepository.technologieExists(technologyDTO.getTechName())).thenReturn(true);
            when(jpaTechnologyRepository.getTechnologyByName(technologyDTO.getTechName())).thenReturn(new Technology());

            projectService.createProject(projectDTO);

            verify(jpaTechnologyRepository, never()).addTechnology(any(Technology.class));
            verify(jpaProjectRepository).create(any(Project.class));
        }

        @Test
        void whenProjectNameAlreadyExists_thenThrowException() {
            when(jpaProjectRepository.existsProjectByName(projectDTO.getName())).thenReturn(true);

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                projectService.createProject(projectDTO);
            });

            assertEquals("Ce nom de projet existe déjà", exception.getMessage());
        }

        @Test
        void whenAddingTeamToProject_thenShouldSetTeam() {
            projectService.createProject(projectDTO);

            verify(teamService).findTeamById(projectDTO.getTeamId());
            verify(jpaProjectRepository).create(any(Project.class));
        }
    }

    @Nested
    class getOrCreateTechnologyTest {
        @Test
        void whenTechnologyDoesNotExist_thenShouldCreateNewTechnology() {
            String techName = "NewTech";
            when(jpaTechnologyRepository.technologieExists(techName)).thenReturn(false);

            Technology result = projectService.getOrCreateTechnology(techName);

            verify(jpaTechnologyRepository).addTechnology(any(Technology.class));
            assertNotNull(result);
            assertEquals(techName, result.getName());
        }

        @Test
        void whenTechnologyExists_thenShouldFetchWithoutCreatingNew() {
            String techName = "ExistingTech";
            Technology existingTech = new Technology();
            existingTech.setName(techName);
            when(jpaTechnologyRepository.technologieExists(techName)).thenReturn(true);
            when(jpaTechnologyRepository.getTechnologyByName(techName)).thenReturn(existingTech);

            Technology result = projectService.getOrCreateTechnology(techName);

            verify(jpaTechnologyRepository, never()).addTechnology(any(Technology.class));
            verify(jpaTechnologyRepository).getTechnologyByName(techName);
            assertNotNull(result);
            assertEquals(techName, result.getName());
        }
    }

    @Nested
    class getProjectFinishedTest {
        @Test
        void whenThereAreFinishedProjects_thenShouldReturnFinishedProjects() {
            Project finishedProject1 = new Project();
            finishedProject1.setStatus("finished");
            Project finishedProject2 = new Project();
            finishedProject2.setStatus("finished");

            List<Project> finishedProjects = Arrays.asList(finishedProject1, finishedProject2);
            when(jpaProjectRepository.getProjectFinished()).thenReturn(finishedProjects);

            List<Project> result = projectService.getProjectFinished();

            assertNotNull(result, "The result should not be null.");
            assertEquals(2, result.size(), "There should be two finished projects.");
            assertTrue(result.stream().allMatch(project -> "finished".equals(project.getStatus())),
                    "All projects should have the status 'finished'.");

            verify(jpaProjectRepository).getProjectFinished();
        }

        @Test
        void whenThereAreNoFinishedProjects_thenShouldReturnEmptyList() {
            // Setup
            when(jpaProjectRepository.getProjectFinished()).thenReturn(new ArrayList<>());

            // Execution
            List<Project> result = projectService.getProjectFinished();

            // Verification
            assertNotNull(result, "The result should not be null.");
            assertTrue(result.isEmpty(), "The result should be an empty list.");

            verify(jpaProjectRepository).getProjectFinished();
        }
    }

    @Nested
    class getProjectPlannedTest {
        @Test
        void whenThereArePlannedProjects_thenShouldReturnPlannedProjects() {
            Project plannedProject1 = new Project();
            plannedProject1.setStatus("planned");
            Project plannedProject2 = new Project();
            plannedProject2.setStatus("planned");

            List<Project> plannedProjects = Arrays.asList(plannedProject1, plannedProject2);
            when(jpaProjectRepository.getProjectPlanned()).thenReturn(plannedProjects);

            List<Project> result = projectService.getProjectPlanned();

            assertNotNull(result, "The result should not be null.");
            assertEquals(2, result.size(), "There should be two planned projects.");
            assertTrue(result.stream().allMatch(project -> "planned".equals(project.getStatus())),
                    "All projects in the result list should have 'planned' status.");

            verify(jpaProjectRepository).getProjectPlanned();
        }

        @Test
        void whenThereAreNoPlannedProjects_thenShouldReturnEmptyList() {
            when(jpaProjectRepository.getProjectPlanned()).thenReturn(new ArrayList<>());

            List<Project> result = projectService.getProjectPlanned();

            assertNotNull(result, "The result should not be null.");
            assertTrue(result.isEmpty(), "The result list should be empty.");

            verify(jpaProjectRepository).getProjectPlanned();
        }
    }

    @Nested
    class getProjectTest {
    }

    @Nested
    class StartProjectTests {
        @Test
        void whenProjectNotFound_thenThrowException() {
            String projectName = "Unknown Project";
            when(jpaProjectRepository.getProjectByName(projectName)).thenReturn(null);

            Exception exception = assertThrows(IllegalArgumentException.class, () -> projectService.startProject(projectName));

            assertEquals("Projet non trouvé avec le nom : " + projectName, exception.getMessage());
        }

        @Test
        void whenProjectNotInPlannedState_thenThrowException() {
            String projectName = "Test Project";
            Project project = new Project();
            project.setName(projectName);
            project.setStatus("inprogress");
            when(jpaProjectRepository.getProjectByName(projectName)).thenReturn(project);

            Exception exception = assertThrows(IllegalArgumentException.class, () -> projectService.startProject(projectName));

            assertEquals("Le projet n'est pas dans un état pouvant être démarré.", exception.getMessage());
        }
    }

    @Nested
    class FinishProjectTests {

        @Test
        void whenProjectDoesNotExist_thenThrowException() {
            when(jpaProjectRepository.getProjectByName(anyString())).thenReturn(null);

            assertThrows(IllegalArgumentException.class, () -> projectService.finishProject("NonExistentProject"));
        }

        @Test
        void whenProjectIsNotInProgress_thenThrowException() {
            Project project = new Project();
            project.setStatus("planned");

            when(jpaProjectRepository.getProjectByName(anyString())).thenReturn(project);

            assertThrows(IllegalArgumentException.class, () -> projectService.finishProject("PlannedProject"));
        }

        @Test
        void whenProjectIsInProgress_thenFinishProject() {
            Project project = new Project();
            project.setStatus("inprogress");

            when(jpaProjectRepository.getProjectByName(anyString())).thenReturn(project);
            when(jpaProjectRepository.updateProject(anyString(), any(Project.class))).thenAnswer(invocation -> invocation.getArgument(1));

            Project finishedProject = projectService.finishProject("InProgressProject");

            assertEquals("finished", finishedProject.getStatus());
            assertNotNull(finishedProject.getEndDate());
            verify(jpaProjectRepository).updateProject(eq("InProgressProject"), any(Project.class));
        }
    }

    @Nested
    class CancelProjectTests {

        @Test
        void whenProjectDoesNotExist_thenThrowException() {
            when(jpaProjectRepository.getProjectByName(anyString())).thenReturn(null);

            assertThrows(IllegalArgumentException.class, () -> projectService.cancelProject("NonExistentProject"));
        }

        @Test
        void whenProjectIsFinished_thenThrowException() {
            Project project = new Project();
            project.setStatus("finished");

            when(jpaProjectRepository.getProjectByName(anyString())).thenReturn(project);

            assertThrows(IllegalArgumentException.class, () -> projectService.cancelProject("FinishedProject"));
        }

        @Test
        void whenProjectIsInProgress_thenThrowException() {
            Project project = new Project();
            project.setStatus("inprogress");

            when(jpaProjectRepository.getProjectByName(anyString())).thenReturn(project);

            assertThrows(IllegalArgumentException.class, () -> projectService.cancelProject("InProgressProject"));
        }

        @Test
        void whenProjectCanBeCanceled_thenCancelProject() {
            Project project = new Project();
            project.setStatus("planned");

            when(jpaProjectRepository.getProjectByName(anyString())).thenReturn(project);
            when(jpaProjectRepository.updateProject(anyString(), any(Project.class))).thenAnswer(invocation -> invocation.getArgument(1));

            Project canceledProject = projectService.cancelProject("CancelableProject");

            assertEquals("cancel", canceledProject.getStatus());
            verify(jpaProjectRepository).updateProject(eq("CancelableProject"), any(Project.class));
        }
    }

    @Nested
    class GetProjectByNameTests {

        @Test
        void whenProjectExists_thenReturnProject() {
            Project expectedProject = new Project();
            expectedProject.setName("ExistingProject");

            when(jpaProjectRepository.getProjectByName("ExistingProject")).thenReturn(expectedProject);

            Project result = projectService.getProjectByName("ExistingProject");

            assertNotNull(result, "The returned project should not be null.");
            assertEquals("ExistingProject", result.getName(), "The project name should match the requested name.");
        }

        @Test
        void whenProjectDoesNotExist_thenReturnNull() {
            when(jpaProjectRepository.getProjectByName("NonExistentProject")).thenReturn(null);

            Project result = projectService.getProjectByName("NonExistentProject");

            assertNull(result, "The returned project should be null for a non-existent project.");
        }
    }

}