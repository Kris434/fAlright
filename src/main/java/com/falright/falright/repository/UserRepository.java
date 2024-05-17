package com.falright.falright.repository;

import com.falright.falright.model.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findUserByUsername(String username);

    Optional<Users> findUserByEmail(String email);
}
