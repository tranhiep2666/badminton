package com.badminton.repository;

import com.badminton.entity.TokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenBlacklistRepository
        extends JpaRepository<
        TokenBlacklist,
        Long> {

    boolean existsByToken(
            String token
    );
}