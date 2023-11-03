package com.project.security.repository;

import com.project.security.model.User;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Getter
public class UserRepository {
    private  List<User> users;

    public UserRepository() {
        users = new ArrayList<>();
        users.add(new User("admin@mail.ua", "password", "admin", List.of("USER", "ADMIN")));
        users.add(new User("user@mail.ua", "password", "user", List.of("USER")));
    }

    public Optional<User> findByUsername(String username) {
        return users.stream().filter(user -> user.getName().equals(username)).findAny();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUserWithUsername(String username) {
        users = users.stream()
                .filter(user -> !user.getName().equalsIgnoreCase(username))
                .toList();
    }


}
