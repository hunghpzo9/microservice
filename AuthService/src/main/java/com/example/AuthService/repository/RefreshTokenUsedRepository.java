package com.example.AuthService.repository;

import com.example.AuthService.domain.KeyToken;
import com.example.AuthService.domain.RefreshTokenUsed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefreshTokenUsedRepository extends JpaRepository<RefreshTokenUsed,Long>,RefreshTokenUsedCustomRepository {
    List<RefreshTokenUsed> findByUserId(Long userId);

}
