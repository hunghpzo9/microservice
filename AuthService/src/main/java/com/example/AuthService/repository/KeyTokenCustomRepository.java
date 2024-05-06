package com.example.AuthService.repository;

import com.example.AuthService.domain.dto.KeyPairDTO;
import com.example.AuthService.domain.dto.KeyTokenDTO;
import com.example.AuthService.domain.dto.UserDTO;
import com.example.AuthService.domain.dto.outDTO.BaseOutDTO;

import java.util.List;

public interface KeyTokenCustomRepository {
    List<KeyTokenDTO> findKeyTokenByUserId(Long userId, String status);

}
