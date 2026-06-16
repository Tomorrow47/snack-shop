package com.snackshop.repository;

import com.snackshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndStatus(String username, Integer status);
    boolean existsByUsername(String username);
}
