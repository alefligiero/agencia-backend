package com.alefligiero.agenciabackend.services;

import com.alefligiero.agenciabackend.repositories.UserRepository;
import com.alefligiero.agenciabackend.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws ResourceNotFoundException {
        return userRepository
                .findByEmailIgnoreCase(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

}
