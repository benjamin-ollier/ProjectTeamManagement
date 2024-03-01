package org.example.Service;

import org.example.Model.DevSkill;
import org.example.Model.Dto.AvailableDev;
import org.example.Model.Dto.DeveloperProfileDTO;
import org.example.Model.Project;
import org.example.Model.User;
import org.example.Model.UserId;
import org.example.Repository.JpaDevSkillRepository;
import org.example.Repository.JpaProjectRepository;
import org.example.Repository.JpaUserRepository;
import org.example.Shared.Util;

import java.util.List;

public class UserService {
    private final JpaUserRepository jpaUserRepository;

    private final JpaProjectRepository jpaProjectRepository;

    private final JpaDevSkillRepository jpaDevSkillRepository;


    public UserService(JpaUserRepository jpaUserRepository, JpaProjectRepository jpaProjectRepository, JpaDevSkillRepository jpaDevSkillRepository) {
        this.jpaUserRepository = jpaUserRepository;
        this.jpaProjectRepository = jpaProjectRepository;
        this.jpaDevSkillRepository = jpaDevSkillRepository;
    }

    public List<User> getAllUsers() {
        return jpaUserRepository.getAllUsers();
    }

    public User getUserByName(String name) {
        return jpaUserRepository.getUserByName(name);
    }

    public User getUserByEmail(String email) {
        return jpaUserRepository.getUserByEmail(email);
    }


    public User updateUser(String identity, User userToUpdate) {
        User currentUser;

        if (Util.isEmail(identity)) {
            currentUser = jpaUserRepository.getUserByEmail(identity);
        } else {
            currentUser = jpaUserRepository.getUserByName(identity);
        }
        if (currentUser == null) {
            throw new IllegalArgumentException("User not found for identity: " + identity);
        }
        UserId userId = new UserId(userToUpdate.getUserIdentifiant().getName(),userToUpdate.getUserIdentifiant().getEmail());

        currentUser.setUserIdentifiant(userId);
        currentUser.setRole(userToUpdate.getRole());


        return jpaUserRepository.update(currentUser);
    }

    public User createUser(User user) {
        return jpaUserRepository.create(user);
    }

    public boolean deleteUser(String name, String email) {
        return jpaUserRepository.deleteUserByNameAndEmail(name, email);
    }

    public DeveloperProfileDTO getDeveloperCV(String nameOrEmail) {
        User currentUser;
        if (Util.isEmail(nameOrEmail)) {
            currentUser = jpaUserRepository.getUserByEmail(nameOrEmail);
        } else {
            currentUser = jpaUserRepository.getUserByName(nameOrEmail);
        }
        if (currentUser == null) {
            throw new IllegalArgumentException("User not found for identity: " + nameOrEmail);
        }

        List<DevSkill> devSkills = jpaDevSkillRepository.getUserSkills(currentUser.getUserIdentifiant().getName());

        List<Project> projects = jpaProjectRepository.getUserProjects(currentUser);

        DeveloperProfileDTO developerProfile = new DeveloperProfileDTO(currentUser, devSkills, projects);
        return developerProfile;
    }

}
