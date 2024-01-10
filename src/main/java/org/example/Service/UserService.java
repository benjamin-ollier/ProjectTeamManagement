package org.example.Service;

import org.example.Model.DevSkill;
import org.example.Model.Technology;
import org.example.Model.User;
import org.example.Repository.UserRepository;
import org.example.Shared.Util;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public User getUserByName(String name) {
        return userRepository.getUserByName(name);
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }
/*
    public User createUser(User user) {
    }*/

    public User updateUser(String identity, User userToUpdate) {
        User currentUser;

        if (Util.isEmail(identity)) {
            currentUser = userRepository.getUserByEmail(identity);
        } else {
            currentUser = userRepository.getUserByName(identity);
        }
        if (currentUser == null) {
            return null;
        }
        currentUser.setName(userToUpdate.getName());
        currentUser.setEmail(userToUpdate.getEmail());
        currentUser.setRole(userToUpdate.getRole());


        return userRepository.update(currentUser);
    }

    public User createUser(User user) {
        return userRepository.create(user);
    }

    public boolean deleteUser(String name, String email) {
        return userRepository.deleteUserByNameAndEmail(name, email);
    }

    public List<DevSkill> getUserSkills(String identity){
        return userRepository.getUserSkills(identity);
    }

    public DevSkill addSkill(String identity, String technoName){
        return null;
    }
    //check si il est deja dans une team
    //check si c'est un dev

}
