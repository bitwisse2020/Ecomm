package com.example.user_service.controller;

import com.example.common.DTO.service_user.AddressResponse;
import com.example.common.DTO.service_user.UserResponse;
import com.example.user_service.dto.AddressRequest;
import com.example.user_service.dto.PasswordResetRequest;
import com.example.user_service.dto.UserRegistrationRequest;
import com.example.user_service.exception.UserNotFoundException;
import com.example.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRegistrationRequest userRegistrationRequest){
        UserResponse userResponse=userService.registerUser(userRegistrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @GetMapping("/{userId}")
    //@PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUserById(@PathVariable Long userId) throws UserNotFoundException {
        return userService.getUserById(userId);
    }

    // 5. List all users (Paginated, Admin-only)
//    @GetMapping
//    //@PreAuthorize("hasRole('ADMIN')")
//    public List<UserResponse> getAllUsers(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        return userService.getAllUsers(PageRequest.of(page, size));
//    }
//
//    // 6. Get user addresses (Owner or Admin)
//    @GetMapping("/{userId}/addresses")
//    //@PreAuthorize("#userId == principal.id or hasRole('ADMIN')")
//    public List<AddressResponse> getUserAddresses(@PathVariable Long userId) {
//        return userService.getUserAddresses(userId);
//    }
//
//    // 7. Add new address (Owner or Admin)
//    @PostMapping("/{userId}/addresses")
//    @ResponseStatus(HttpStatus.CREATED)
//    //@PreAuthorize("#userId == principal.id or hasRole('ADMIN')")
//    public AddressResponse addAddress(
//            @PathVariable Long userId,
//            @Valid @RequestBody AddressRequest request) {
//        return userService.addAddress(userId, request);
//    }
//
//    // 8. Initiate password reset (Public)
//    @PostMapping("/password-reset")
//    public void initiatePasswordReset(@RequestParam String email) {
//        userService.initiatePasswordReset(email);
//    }
//
//    // 9. Complete password reset (Public)
//    @PutMapping("/password-reset/{token}")
//    public void completePasswordReset(
//            @PathVariable String token,
//            @Valid @RequestBody PasswordResetRequest request) {
//        userService.completePasswordReset(token, request);
//    }
//
//    // 10. Deactivate account (Owner or Admin)
//    @PutMapping("/{userId}/deactivate")
//    //@PreAuthorize("#userId == principal.id or hasRole('ADMIN')")
//    public void deactivateUser(@PathVariable Long userId) {
//        userService.deactivateUser(userId);
//    }
}
