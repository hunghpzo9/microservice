package com.example.AuthService.repository;

import com.example.AuthService.domain.dto.UserDTO;
import com.example.AuthService.utils.Const;

import java.util.List;

public interface UserCustomRepository {
    List<UserDTO> findUserByEmail(String email, String status);
}
