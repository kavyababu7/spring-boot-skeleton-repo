package com.nissan.template.service;

import com.nissan.template.domain.User;
import com.nissan.template.dto.UserDTO;
import com.nissan.template.exception.BusinessException;
import com.nissan.template.mapper.UserMapper;
import com.nissan.template.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toDto);
    }

    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new BusinessException("User not found", HttpStatus.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public UserDTO getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toDto)
                .orElseThrow(() -> new BusinessException("User not found", HttpStatus.NOT_FOUND));
    }

    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("User not found", HttpStatus.NOT_FOUND));
        
        user.setEmail(userDto.getEmail());
        // Username change might require more complex logic (validation, etc.)
        user.setUsername(userDto.getUsername());
        
        return userMapper.toDto(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new BusinessException("User not found", HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id);
    }
}
