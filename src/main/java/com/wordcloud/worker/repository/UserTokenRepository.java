package com.wordcloud.worker.repository;

import com.wordcloud.worker.model.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserTokenRepository extends JpaRepository<UserToken, UUID> {
    UserToken findByToken(String token);
}