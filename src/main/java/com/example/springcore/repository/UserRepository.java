package com.example.springcore.repository;

import com.example.springcore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByKakaoId(Long kakaoId);

    Optional<User> findByEmail(String email);
}
