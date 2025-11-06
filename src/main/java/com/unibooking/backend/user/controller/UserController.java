package com.unibooking.backend.user.controller;

import com.unibooking.backend.Exception.UserAlreadyExistsException;
import com.unibooking.backend.Exception.UserNotFoundException;
import com.unibooking.backend.user.dto.UserDTO;
import com.unibooking.backend.user.model.UserModel;
import com.unibooking.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Get current user profile
    @GetMapping("/currentUser")
    public ResponseEntity<Optional<UserModel>> getProfile(@RequestParam String email) throws UserNotFoundException {
        Optional<UserModel> user = userService.getCurrentUserProfile(email);
        return ResponseEntity.ok(user);
    }

    // Register new user
    @PostMapping("/register")
    public ResponseEntity<Void> createNewUser(@RequestBody UserDTO userDTO) throws UserAlreadyExistsException {
        userService.createUserProfile(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // delete user
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User profile deleted successfully!");
    }
}

