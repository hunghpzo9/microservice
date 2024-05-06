package com.example.AuthService.repository;

import com.example.AuthService.domain.KeyToken;
import com.example.AuthService.domain.dto.outDTO.BaseOutDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeyTokenRepository extends JpaRepository<KeyToken,Long>,KeyTokenCustomRepository{
    List<KeyToken> findByUserId(Long userId);

}
