package com.nissan.template.service;

import com.nissan.template.domain.User;
import com.nissan.template.dto.UserDTO;
import com.nissan.template.exception.BusinessException;
import com.nissan.template.mapper.UserMapper;
import com.nissan.template.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Unit Tests")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDTO userDto;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .username("testuser")
                .email("test@nissan.com")
                .password("password")
                .build();
        user.setId(1L);

        userDto = UserDTO.builder()
                .id(1L)
                .username("testuser")
                .email("test@nissan.com")
                .build();
    }

    @Test
    @DisplayName("Should return all users in a paged format")
    void shouldReturnAllUsers_whenPageableIsProvided() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> userPage = new PageImpl<>(List.of(user));
        given(userRepository.findAll(pageable)).willReturn(userPage);
        given(userMapper.toDto(any(User.class))).willReturn(userDto);

        // When
        Page<UserDTO> result = userService.getAllUsers(pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getUsername()).isEqualTo("testuser");
        verify(userRepository).findAll(pageable);
    }

    @Test
    @DisplayName("Should return a user DTO when ID exists")
    void shouldReturnUserDto_whenUserIdExists() {
        // Given
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(userMapper.toDto(user)).willReturn(userDto);

        // When
        UserDTO result = userService.getUserById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(userRepository).findById(1L);
    }

    @Test
    @DisplayName("Should throw BusinessException when ID does not exist")
    void shouldThrowException_whenUserIdDoesNotExist() {
        // Given
        given(userRepository.findById(1L)).willReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> userService.getUserById(1L))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND)
                .hasMessage("User not found");
    }

    @Test
    @DisplayName("Should update and return user DTO when user exists")
    void shouldUpdateUser_whenUserExists() {
        // Given
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(userRepository.save(any(User.class))).willReturn(user);
        given(userMapper.toDto(any(User.class))).willReturn(userDto);

        // When
        UserDTO result = userService.updateUser(1L, userDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("testuser");
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should delete user when user exists")
    void shouldDeleteUser_whenUserExists() {
        // Given
        given(userRepository.existsById(1L)).willReturn(true);

        // When
        userService.deleteUser(1L);

        // Then
        verify(userRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw BusinessException on delete when user does not exist")
    void shouldThrowException_whenDeletingNonExistentUser() {
        // Given
        given(userRepository.existsById(1L)).willReturn(false);

        // When & Then
        assertThatThrownBy(() -> userService.deleteUser(1L))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND);
    }
}
