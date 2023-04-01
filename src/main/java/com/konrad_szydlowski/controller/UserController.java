package com.konrad_szydlowski.controller;

import com.konrad_szydlowski.exception.ResourceNotFoundException;
import com.konrad_szydlowski.model.User;
import com.konrad_szydlowski.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUser(){
        return userRepository.findAll();
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id")Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found on: "
                + userId));
        return ResponseEntity.ok().body(user);
    }
    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user){
        return userRepository.save(user);
    }

    @DeleteMapping("/users/{id}")
    public Map<String, Boolean> deleteUserById(@PathVariable(value = "id")Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found on: "
                + userId));
        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> editUserById(@PathVariable(value = "id")Long userId,
                                             @Valid @RequestBody User u) throws ResourceNotFoundException{
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on: " + userId));

        if (u.getFirstName() != null) user.setFirstName(u.getFirstName());
        if (u.getLastName() != null) user.setLastName(u.getLastName());
        if (u.getEmail() != null) user.setEmail(u.getEmail());

        User user1 = userRepository.save(user);

        return ResponseEntity.ok(user1);
    }

}
