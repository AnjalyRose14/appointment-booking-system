package com.unibooking.backend.user.service;

import com.unibooking.backend.Exception.UserAlreadyExistsException;
import com.unibooking.backend.Exception.UserNotFoundException;
import com.unibooking.backend.user.dto.*;
import com.unibooking.backend.user.model.UserModel;
import com.unibooking.backend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    @Override
    public UserDTO getUserProfile(String email) throws UserNotFoundException {
        UserModel user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        return convertToDTO(user);

    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserModel> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Helper method to convert Model → DTO
    public UserDTO convertToDTO(UserModel user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setUserName(user.getUserName());
        dto.setUserEmail(user.getUserEmail());
        dto.setUserPhone(user.getUserPhone());
        return dto;  //    Don’t include password for security!
    }


    @Override
    public Boolean createUserProfile(RegisterDTO registerDTO) throws UserAlreadyExistsException {
        if (userRepository.findByUserEmail(registerDTO.getUserEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already registered: " + registerDTO.getUserEmail());
        }
        UserModel user = new UserModel();
        user.setUserName(registerDTO.getUserName());
        user.setUserEmail(registerDTO.getUserEmail());
        user.setUserPassword(registerDTO.getUserPassword());
        user.setUserPhone(registerDTO.getUserPhone());

        userRepository.save(user);
        return true;
    }

    @Override
    public Boolean loginUser(LoginDTO loginDTO) {
        Optional<UserModel> optionalUser = userRepository.findByUserEmail(loginDTO.getUserEmail());
        if (optionalUser.isPresent()) {
            UserModel user = optionalUser.get();

            if (user.getUserPassword().equals(loginDTO.getUserPassword())) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public UserModel updateUserProfile(UpdateDTO updateDTO) throws UserNotFoundException {
        UserModel existingUser = userRepository.findByUserEmail(updateDTO.getUserEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + updateDTO.getUserEmail()));

        existingUser.setUserName(updateDTO.getUserName());
        existingUser.setUserPassword(updateDTO.getUserPassword()); // hash in real apps
        existingUser.setUserPhone(updateDTO.getUserPhone());

        return userRepository.save(existingUser);

    }


    @Override
    public void deleteUser(Long id) throws UserNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User with ID " + id + " not found.");
        }
        userRepository.deleteById(id);
    }


}