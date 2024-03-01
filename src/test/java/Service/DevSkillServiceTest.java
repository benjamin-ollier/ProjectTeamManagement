package Service;

import org.example.Model.DevSkill;
import org.example.Model.Technology;
import org.example.Model.User;
import org.example.Model.UserId;
import org.example.Repository.Interface.DevSkillRepository;
import org.example.Repository.JpaTechnologyRepository;
import org.example.Repository.JpaUserRepository;
import org.example.Service.DevSkillService;
import org.example.Shared.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
@ExtendWith(MockitoExtension.class)
public class DevSkillServiceTest {
    @Mock
    private DevSkillRepository devSkillRepository;
    @Mock
    private JpaTechnologyRepository jpaTechnologyRepository;
    @Mock
    private JpaUserRepository jpaUserRepository;

    @InjectMocks
    private DevSkillService devSkillService;

    private User user;
    private Technology tech;
    private DevSkill devSkill;
    @BeforeEach
    void setUp() {
        UserId userId = new UserId("testName", "test@example.com");

        User user = new User();
        user.setUserIdentifiant(userId);

        tech = new Technology(1, "java");


        DevSkill devSkill = new DevSkill();
        devSkill.setUserIdentity(user);
        devSkill.setTechId(tech);
        devSkill.setYearsOfExperience(5);

        when(jpaUserRepository.getUserByEmail(anyString())).thenReturn(user);
    }
    @Nested
    class AddOrUpdateSkillTests {

        private final UserId identity = new UserId("testName", "test@example.com");
        private final String technology = "Java";
        private final int yearsOfExperience = 5;

        @Test
        void whenUserNotFound_thenThrowException() {
            when(jpaUserRepository.getUserByEmail(anyString())).thenReturn(null);

            assertThrows(IllegalArgumentException.class, () -> devSkillService.addOrUpdateSkill(identity.getEmail(), technology, yearsOfExperience));
        }


        @Test
        void whenExistingTechnologyAndNewDevSkill_thenCreateDevSkill() {
            UserId userId = new UserId("testName", "test@example.com");
            User user = new User();
            user.setUserIdentifiant(userId);

            String technologyName = "Java";
            Technology tech = new Technology();
            tech.setName(technologyName);

            int yearsOfExperience = 5;

            when(jpaUserRepository.getUserByEmail(user.getUserIdentifiant().getEmail())).thenReturn(user);
            when(jpaTechnologyRepository.technologieExists(tech.getName())).thenReturn(true);
            when(jpaTechnologyRepository.getTechnologyByName(tech.getName())).thenReturn(tech);
            when(devSkillRepository.save(any(DevSkill.class))).thenAnswer(invocation -> invocation.getArgument(0));

            DevSkill result = devSkillService.addOrUpdateSkill(user.getUserIdentifiant().getEmail(), tech.getName(), yearsOfExperience);

            assertNotNull(result);
            assertAll("DevSkill properties",
                    () -> assertEquals(user, result.getUserIdentity(), "User should match"),
                    () -> assertEquals(tech, result.getTechId(), "Technology should match"),
                    () -> assertEquals(yearsOfExperience, result.getYearsOfExperience(), "Years of experience should match")
            );

            verify(jpaUserRepository).getUserByEmail(user.getUserIdentifiant().getEmail());
            verify(jpaTechnologyRepository).technologieExists(tech.getName());
            verify(jpaTechnologyRepository).getTechnologyByName(tech.getName());
            verify(devSkillRepository).save(any(DevSkill.class));
        }


        @Test
        void whenExistingDevSkill_thenUpdateExperienceAndLevel() {
            // Initialisation des objets nécessaires pour le test
            UserId userId = new UserId("testName", "test@example.com");
            User user = new User();
            user.setUserIdentifiant(userId);

            String technologyName = "Java";
            Technology tech = new Technology();
            tech.setName(technologyName);

            int initialYearsOfExperience = 2; // Années d'expérience initiales
            String initialLevel = Util.ExperienceCategory.JUNIOR.toString(); // Niveau initial
            DevSkill existingDevSkill = new DevSkill();
            existingDevSkill.setUserIdentity(user);
            existingDevSkill.setTechId(tech);
            existingDevSkill.setYearsOfExperience(initialYearsOfExperience);
            existingDevSkill.setLevel(initialLevel);

            int updatedYearsOfExperience = 6; // Nouvelles années d'expérience pour la mise à jour
            String expectedUpdatedLevel = Util.ExperienceCategory.EXPERT.toString(); // Niveau attendu après la mise à jour

            // Configuration des mocks
            when(jpaUserRepository.getUserByEmail(anyString())).thenReturn(user);
            when(jpaTechnologyRepository.technologieExists(anyString())).thenReturn(true);
            when(jpaTechnologyRepository.getTechnologyByName(anyString())).thenReturn(tech);
            when(devSkillRepository.getDevSkillIfExists(anyString(), anyString(), anyInt())).thenReturn(existingDevSkill);
            when(devSkillRepository.update(any(DevSkill.class))).thenAnswer(invocation -> invocation.getArgument(0));

            // Exécution de la méthode à tester
            DevSkill result = devSkillService.addOrUpdateSkill(userId.getEmail(), technologyName, updatedYearsOfExperience);

            // Vérifications
            assertNotNull(result, "Le DevSkill mis à jour ne devrait pas être null");
            assertEquals(updatedYearsOfExperience, result.getYearsOfExperience(), "Les années d'expérience devraient être mises à jour");
            assertEquals(expectedUpdatedLevel, result.getLevel(), "Le niveau devrait être mis à jour selon les années d'expérience");

            // Vérifier que les méthodes attendues ont été appelées sur les mocks
            verify(devSkillRepository).getDevSkillIfExists(userId.getName(), userId.getEmail(), tech.getTechId());
            verify(devSkillRepository).update(any(DevSkill.class));
        }

        @Test
        void whenAddOrUpdateSkillWithInvalidExperience_thenThrowException() {
            String identity = "john.doe@example.com";
            String techName = "Java";
            int invalidYearsOfExperience = -1;

            IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
                devSkillService.addOrUpdateSkill(identity, techName, invalidYearsOfExperience);
            });

            assertEquals("Année d'experience pas correcte", thrown.getMessage());
        }


    }

    @Nested
    class GetUserByIdentityTests {
        @Test
        void whenIdentityIsEmailAndUserExists_thenSuccess() {
            String email = "user@example.com";
            User expectedUser = new User();
            when(jpaUserRepository.getUserByEmail(email)).thenReturn(expectedUser);

            User result = jpaUserRepository.getUserByEmail(email);

            assertNotNull(result);
            assertEquals(expectedUser, result);
            verify(jpaUserRepository).getUserByEmail(email);
        }

        @Test
        void whenIdentityIsEmail_thenSuccess() {
            String email = "user@example.com";
            User expectedUser = new User();
            expectedUser.setUserIdentifiant(new UserId("username", email));

            when(jpaUserRepository.getUserByEmail(email)).thenReturn(expectedUser);

            User result = devSkillService.getUserByIdentity(email);

            assertNotNull(result);
            assertEquals(expectedUser, result);
            verify(jpaUserRepository).getUserByEmail(email);
            verify(jpaUserRepository, never()).getUserByName(anyString());
        }



    }

    @Nested
    class getOrCreateTechnologyTests{

    }
}