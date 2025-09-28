package com.example.springboothello.controller;

import com.example.springboothello.model.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private WebClient supabaseClient;

    @GetMapping("")
    public List<User> getAllUsers() {
        String response = supabaseClient.get()
                .uri("/rest/v1/users?select=*")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        
        List<User> users = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(response);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            User user = new User();
            user.setId(jsonObj.getLong("id"));
            user.setName(jsonObj.getString("name"));
            user.setEmail(jsonObj.getString("email"));
            user.setCreatedAt(jsonObj.getString("created_at"));
            users.add(user);
        }
        return users;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        String response = supabaseClient.get()
                .uri("/rest/v1/users?id=eq." + id + "&select=*")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        
        JSONArray jsonArray = new JSONArray(response);
        if (jsonArray.length() > 0) {
            JSONObject jsonObj = jsonArray.getJSONObject(0);
            User user = new User();
            user.setId(jsonObj.getLong("id"));
            user.setName(jsonObj.getString("name"));
            user.setEmail(jsonObj.getString("email"));
            user.setCreatedAt(jsonObj.getString("created_at"));
            return user;
        }
        return null;
    }

    @PostMapping("")
    public User createUser(@RequestBody User user) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("name", user.getName());
        jsonObj.put("email", user.getEmail());
        
        String response = supabaseClient.post()
                .uri("/rest/v1/users")
                .bodyValue(jsonObj.toString())
                .header("Prefer", "return=representation")
                .header("Content-Type", "application/json")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        
        JSONArray jsonArray = new JSONArray(response);
        JSONObject responseObj = jsonArray.getJSONObject(0);
        user.setId(responseObj.getLong("id"));
        user.setCreatedAt(responseObj.getString("created_at"));
        return user;
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("name", user.getName());
        jsonObj.put("email", user.getEmail());
        
        String response = supabaseClient.patch()
                .uri("/rest/v1/users?id=eq." + id)
                .bodyValue(jsonObj.toString())
                .header("Prefer", "return=representation")
                .header("Content-Type", "application/json")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        
        JSONArray jsonArray = new JSONArray(response);
        JSONObject responseObj = jsonArray.getJSONObject(0);
        user.setId(responseObj.getLong("id"));
        user.setCreatedAt(responseObj.getString("created_at"));
        return user;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        supabaseClient.delete()
                .uri("/rest/v1/users?id=eq." + id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
