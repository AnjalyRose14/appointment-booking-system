package com.unibooking.backend.user.controller;

import com.unibooking.backend.Exception.UserAlreadyExistsException;
import com.unibooking.backend.Exception.UserNotFoundException;
import com.unibooking.backend.user.dto.LoginDTO;
import com.unibooking.backend.user.dto.RegisterDTO;
import com.unibooking.backend.user.dto.UpdateDTO;
import com.unibooking.backend.user.dto.UserDTO;
import com.unibooking.backend.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Get user profile by email
    @GetMapping("/currentUser")
    public ResponseEntity<UserDTO> getProfile(@RequestParam String email) throws UserNotFoundException {
        UserDTO userDTO = userService.getUserProfile(email);
        return ResponseEntity.ok(userDTO);
    }

    // Get all users
    @GetMapping("/allUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Register new user
//    @PostMapping("/users/{id}")
//    public ResponseEntity<Void> createNewUser(@RequestBody RegisterDTO registerDTO) throws UserAlreadyExistsException {
//        userService.createUserProfile(registerDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }


    //User Login
//    @PostMapping("/login")
//    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginDTO loginDTO){
//        boolean isAuthenticated = userService.loginUser(loginDTO);
//
//        if (isAuthenticated) {
//            return ResponseEntity.ok("Login successful!");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
//        }
//    }

    // Update user details
    @PutMapping("/update")
    public ResponseEntity<String> updateUserProfile(@RequestBody UpdateDTO updateDTO) throws UserNotFoundException {
        userService.updateUserProfile(updateDTO);
        return ResponseEntity.ok("User profile updated successfully!");
    }

    // delete user
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User profile deleted successfully!");
    }
}




