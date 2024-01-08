package org.example.Service;

import org.example.Model.User;
import org.example.Repository.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    /*
    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(User user) {
        userRepository.save(user); //'assurer qu'il existe
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }*/

}
