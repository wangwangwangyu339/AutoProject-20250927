package com.example.springboothello.controller;

import com.example.springboothello.model.User;
import io.github.supabase.SupabaseClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private SupabaseClient supabaseClient;

    @GetMapping("")
    public List<User> getAllUsers() {
        return supabaseClient.rest().table("users")
                .select()
                .executeAndGetList(User.class);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return supabaseClient.rest().table("users")
                .select()
                .eq("id", id)
                .executeAndGetSingle(User.class);
    }

    @PostMapping("")
    public User createUser(@RequestBody User user) {
        return supabaseClient.rest().table("users")
                .insert(user)
                .executeAndGetSingle(User.class);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return supabaseClient.rest().table("users")
                .update(user)
                .eq("id", id)
                .executeAndGetSingle(User.class);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        supabaseClient.rest().table("users")
                .delete()
                .eq("id", id)
                .execute();
    }
}
