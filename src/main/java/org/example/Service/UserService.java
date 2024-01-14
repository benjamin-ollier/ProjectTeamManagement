package org.example.Service;

import org.example.Model.DevSkill;
import org.example.Model.Technology;
import org.example.Model.User;
import org.example.Model.UserId;
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
            throw new IllegalArgumentException("User not found for identity: " + identity);
        }
        UserId userId = new UserId(userToUpdate.getUserId().getName(),userToUpdate.getUserId().getEmail());

        currentUser.setUserId(userId);
        currentUser.setRole(userToUpdate.getRole());


        return userRepository.update(currentUser);
    }

    public User createUser(User user) {
        return userRepository.create(user);
    }

    public boolean deleteUser(String name, String email) {
        return userRepository.deleteUserByNameAndEmail(name, email);
    }


}
