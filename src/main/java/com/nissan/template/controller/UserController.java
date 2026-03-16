package com.nissan.template.controller;

import com.nissan.template.common.response.ApiResponse;
import com.nissan.template.dto.UserDTO;
import com.nissan.template.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ApiResponse<Page<UserDTO>> getAllUsers(Pageable pageable) {
        return ApiResponse.success(userService.getAllUsers(pageable));
    }

    @GetMapping("/{id}")
    public ApiResponse<UserDTO> getUserById(@PathVariable Long id) {
        return ApiResponse.success(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDto) {
        return ApiResponse.success(userService.updateUser(id, userDto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ApiResponse.success(null);
    }
}
