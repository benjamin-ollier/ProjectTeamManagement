package org.example.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.Repository.JsonUtil;
import org.example.Modele.User;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class UserService {
    public List<User> getAllUsers() {
        try {
            return JsonUtil.readFromJsonFile("data/User.json", new TypeReference<List<User>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
