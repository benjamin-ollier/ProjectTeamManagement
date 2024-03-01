package Service;

import org.example.Model.*;
import org.example.Model.Dto.DeveloperProfileDTO;
import org.example.Repository.JpaDevSkillRepository;
import org.example.Repository.JpaProjectRepository;
import org.example.Repository.JpaUserRepository;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private JpaUserRepository userRepository;

    @Mock
    private JpaDevSkillRepository devSkillRepository;

    @Mock
    private JpaProjectRepository projectRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private List<DevSkill> devSkills;
    private List<Project> projects;

    @BeforeEach
    void setUp() {
        UserId userId = new UserId("testName", "test@example.com");
        user = new User();
        user.setUserIdentifiant(userId);
        user.setRole("Developer");
        devSkills = Arrays.asList(new DevSkill());
        projects = Arrays.asList(new Project());

        lenient().when(userRepository.getUserByEmail("test@example.com")).thenReturn(user);
        lenient().when(userRepository.getUserByName(anyString())).thenReturn(user);
        lenient().when(devSkillRepository.getUserSkills(anyString())).thenReturn(devSkills);
        lenient().when(projectRepository.getUserProjects(any())).thenReturn(projects);
    }

    @Test
    void createUser_CreateInRepository() {
        when(userRepository.create(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(new User());

        verify(userRepository).create(any(User.class));
        assertNotNull(createdUser, "The returned user should not be null.");
    }

    @Nested
    class deleteUserTests {
        @Test
        void deleteUser_WhenUserExists_ReturnsTrue() {
            when(userRepository.deleteUserByNameAndEmail(anyString(), anyString())).thenReturn(true);

            boolean result = userService.deleteUser("testName", "test@example.com");

            assertTrue(result, "Should return true when the user is successfully deleted.");
        }

        @Test
        void deleteUser_WhenUserDoesNotExist_ReturnsFalse() {
            when(userRepository.deleteUserByNameAndEmail(anyString(), anyString())).thenReturn(false);

            boolean result = userService.deleteUser("nonExistentName", "nonExistent@example.com");

            assertFalse(result, "Should return false when the user does not exist.");
        }
    }

    @Test
    void getDeveloperCV_ReturnsCorrectData() {
        DeveloperProfileDTO result = userService.getDeveloperCV("test@example.com");

        assertNotNull(result, "The result should not be null.");
        assertEquals(user, result.getUser(), "The user in the CV should match the expected user.");
        assertEquals(devSkills, result.getDevSkills(), "The dev skills in the CV should match the expected dev skills.");
        assertEquals(projects, result.getProjects(), "The projects in the CV should match the expected projects.");
    }
}
