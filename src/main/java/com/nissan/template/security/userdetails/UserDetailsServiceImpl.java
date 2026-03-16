package com.nissan.template.security.userdetails;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    // Ideally, this would use a UserRepository. For the template, we'll provide a placeholder.
    // In the User module, we will implement this properly.
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Placeholder implementation for initial setup
        if ("admin".equals(username)) {
            return UserDetailsImpl.builder()
                    .username("admin")
                    .password("$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07IxS.Vd8HVkC.XByS") // "password"
                    .authorities(Collections.emptyList())
                    .build();
        }
        throw new UsernameNotFoundException("User not found: " + username);
    }
}
