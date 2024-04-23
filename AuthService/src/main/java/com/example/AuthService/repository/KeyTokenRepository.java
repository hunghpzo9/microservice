package com.example.AuthService.repository;

import com.example.AuthService.domain.KeyToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyTokenRepository extends JpaRepository<KeyToken,Long>,KeyTokenCustomRepository{

}
