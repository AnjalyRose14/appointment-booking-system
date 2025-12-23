package com.unibooking.backend.user.service;

import com.unibooking.backend.Exception.UserNotFoundException;
import com.unibooking.backend.user.dto.*;
import com.unibooking.backend.user.model.UserModel;

import java.util.List;

public interface UserService {

    // Get logged-in user's profile by email (email extracted from JWT)
    UserDTO getUserProfile(String email) throws UserNotFoundException;

    // Return all users (admin-only use)
    List<UserDTO> getAllUsers();

    //  Update an existing user profile identified by email (the caller's email from token)
    void updateUserProfile(String email, UserUpdateDTO updateDTO) throws UserNotFoundException;

    // Delete a user by id (admin-only)
    void deleteUser(Long id) throws UserNotFoundException;

    //Delete the user identified by email (self-delete use-case)
    void deleteUserByEmail(String email) throws UserNotFoundException;


    //Convert a UserModel entity to UserDTO
    UserDTO convertToDTO(UserModel userModel);
}
