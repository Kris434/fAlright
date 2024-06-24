package com.falright.falright.service;

import com.falright.falright.model.Users;
import com.falright.falright.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Optional<Users> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Transactional
    public Optional<Users> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public boolean emailExists(String email) {
        return findUserByEmail(email).isPresent();
    }

    public boolean usernameExists(String username) {
        return findUserByUsername(username).isPresent();
    }

    @Transactional
    public Users saveUser (Users user) {
        return userRepository.save(user);
    }

    public Users registerUser(Users user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setRole(Users.Role.USER);
        return saveUser(user);
    }

    public void changeUserPassword(Users user, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public List<Users> getAllNonAdminUsers() { return userRepository.findByRoleNot(Users.Role.ADMIN); }

    public void assignRole(String username, Users.Role role) {
        Users user = userRepository.findUserByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(role);
        userRepository.save(user);
    }
}
