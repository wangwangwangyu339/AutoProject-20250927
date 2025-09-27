package com.example.springboothello.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final List<Map<String, Object>> users = new ArrayList<>();
    static {
        Map<String, Object> user1 = new HashMap<>();
        user1.put("id", 1);
        user1.put("name", "Alice");
        users.add(user1);
        Map<String, Object> user2 = new HashMap<>();
        user2.put("id", 2);
        user2.put("name", "Bob");
        users.add(user2);
    }

    @GetMapping("")
    public List<Map<String, Object>> getAllUsers() {
        return users;
    }

    @GetMapping("/{id}")
    public Map<String, Object> getUserById(@PathVariable int id) {
        return users.stream().filter(u -> u.get("id").equals(id)).findFirst().orElse(null);
    }

    @PostMapping("")
    public Map<String, Object> createUser(@RequestBody Map<String, Object> user) {
        user.put("id", users.size() + 1);
        users.add(user);
        return user;
    }
}
