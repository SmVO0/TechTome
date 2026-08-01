package com.SVO.TechTome.repositories;

import com.SVO.TechTome.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Page<User> findByEmailContainingIgnoreCaseOrUsernameContainingIgnoreCase(
            String email, String username, Pageable pageable);
}
