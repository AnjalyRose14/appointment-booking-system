package com.unibooking.backend.user.controller;

import com.unibooking.backend.Exception.UserNotFoundException;
import com.unibooking.backend.user.dto.UserUpdateDTO;
import com.unibooking.backend.user.dto.UserDTO;
import com.unibooking.backend.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/currentUser")
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails)
            throws UserNotFoundException {

        String email = userDetails.getUsername();
        UserDTO userDTO = userService.getUserProfile(email);
        return ResponseEntity.ok(userDTO);
    }

    // Admin-only: list all users.
    @GetMapping("/allUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Update user details
    @PutMapping("/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateUserProfile(
            @Valid @RequestBody UserUpdateDTO updateDTO,
             @AuthenticationPrincipal UserDetails userDetails) throws UserNotFoundException {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        String email = userDetails.getUsername();
        userService.updateUserProfile(email, updateDTO);
        return ResponseEntity.ok("User profile updated successfully!");
    }

    // Admin-only: delete any user by id
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User profile deleted successfully!");
    }

    /**
     * Self-delete: an authenticated user can delete their own account.
     * This does not require admin role; it's tied to the JWT identity.
     */
    @DeleteMapping("/deleteCurrentUser")
    public ResponseEntity<Void> deleteCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String email = userDetails.getUsername();
        userService.deleteUserByEmail(email); // implement in service if not present
        return ResponseEntity.noContent().build();
    }
}




