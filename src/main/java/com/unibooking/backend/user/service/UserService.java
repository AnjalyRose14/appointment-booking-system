package com.unibooking.backend.user.service;

import com.unibooking.backend.Exception.UserAlreadyExistsException;
import com.unibooking.backend.Exception.UserNotFoundException;
import com.unibooking.backend.user.dto.*;
import com.unibooking.backend.user.model.UserModel;

import java.util.List;

public interface UserService {

    // Get logged-in user's profile
    UserDTO getUserProfile(String email) throws UserNotFoundException;
    //get all users
    List<UserDTO> getAllUsers();

    // register new user
    Boolean createUserProfile(RegisterDTO registerDTO) throws UserAlreadyExistsException;

    // login user
    Boolean loginUser(LoginDTO loginDTO);

    // Update existing user profile
    UserModel updateUserProfile(UpdateDTO updateDTO) throws UserNotFoundException;

    //delete user
    void deleteUser(Long id) throws UserNotFoundException;


    Object convertToDTO(UserModel userModel);
}
