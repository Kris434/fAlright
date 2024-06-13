package com.falright.falright.repository;

import com.falright.falright.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    public Optional<Users> findUserByUsername(String username);
    public Optional<Users> findUserByEmail(String email);
    public List<Users> findByRoleNot(Users.Role role);
}
