package com.example.mytop100movies.service;

import com.example.mytop100movies.model.User;
import com.example.mytop100movies.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(String username) {
        return userRepository.save(User.builder().username(username).build());
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }
}
