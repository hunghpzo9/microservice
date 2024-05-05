package com.example.AuthService.service;

import com.example.AuthService.domain.dto.inDTO.UserInDTO;
import com.example.AuthService.domain.dto.outDTO.BaseOutDTO;
import com.example.AuthService.domain.dto.UserDTO;
import com.example.AuthService.domain.dto.outDTO.UserOutDTO;

public interface UserService {
    UserDTO findUserByEmail(String email,String status);

}
