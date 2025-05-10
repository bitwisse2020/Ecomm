package com.example.user_service.service;

import com.example.common.DTO.service_user.AddressResponse;
import com.example.common.DTO.service_user.UserResponse;
import com.example.common.Mapper.service_user.UserMapper;
import com.example.user_service.dto.AddressRequest;
import com.example.user_service.dto.PasswordResetRequest;
import com.example.user_service.dto.UserRegistrationRequest;
import com.example.user_service.entity.User;
import com.example.user_service.exception.UserNotFoundException;
import com.example.user_service.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponse registerUser(UserRegistrationRequest userRegistrationRequest) {
        User user = User.builder()
                .firstName(userRegistrationRequest.firstName())
                .lastName(userRegistrationRequest.lastName())
                .username(getUserName(userRegistrationRequest))
                .email(userRegistrationRequest.email())
                .build();
        return userMapper.toResponse(userRepository.save(user));
    }

    private String getUserName(UserRegistrationRequest userRegistrationRequest) {
        String firstName = userRegistrationRequest.firstName();
        String lastName = userRegistrationRequest.lastName();
        return firstName.toLowerCase() + "_" + lastName.toLowerCase();
    }

    public UserResponse getUserById(Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Not found with Id:" + userId));
        return userMapper.toResponse(user);
    }

//    public List<UserResponse> getAllUsers(PageRequest of) {
//    }
//
//    public List<AddressResponse> getUserAddresses(Long userId) {
//    }
//
//    public AddressResponse addAddress(Long userId, @Valid AddressRequest request) {
//    }
//
//    public void initiatePasswordReset(String email) {
//    }
//
//    public void completePasswordReset(String token, @Valid PasswordResetRequest request) {
//    }
}
