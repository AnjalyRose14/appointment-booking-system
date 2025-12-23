package com.unibooking.backend.user.service;

import com.unibooking.backend.Exception.UserNotFoundException;
import com.unibooking.backend.user.dto.*;
import com.unibooking.backend.user.model.UserModel;
import com.unibooking.backend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    //Get by email
    @Override
    public UserDTO getUserProfile(String email) throws UserNotFoundException {
        UserModel user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        return convertToDTO(user);

    }

    //Get All Users (Admin Only)
    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Convert Entity → DTO
    @Override
    public UserDTO convertToDTO(UserModel user) {
        UserDTO dto = new UserDTO();

            dto.setUserId(user.getUserId());
            dto.setUserName(user.getUserName());
            dto.setUserEmail(user.getUserEmail());
            dto.setUserPhone(user.getUserPhone());

            return dto;  //    Don’t include password for security!
    }

    // Update Profile for Logged-in User
    @Override
    @Transactional
    public void updateUserProfile(String email, UserUpdateDTO updateDTO) throws UserNotFoundException {

        UserModel user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + email));

        if (updateDTO.getUserName() != null) {
            user.setUserName(updateDTO.getUserName());
        }

        if (updateDTO.getUserPhone() != null) {
            user.setUserPhone(updateDTO.getUserPhone());
        }

        if (updateDTO.getUserPassword() != null && !updateDTO.getUserPassword().isBlank()) {
            user.setUserPassword(passwordEncoder.encode(updateDTO.getUserPassword()));
        }
        userRepository.save(user);
    }

    // Delete User by ID (Admin)
    @Override
    @Transactional
    public void deleteUser(Long id) throws UserNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User with ID " + id + " not found.");
        }
        userRepository.deleteById(id);
    }

    // Delete own Account (Authenticated User)
    @Override
    @Transactional
    public void deleteUserByEmail(String email) throws UserNotFoundException {
        UserModel user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        userRepository.delete(user);
    }
}