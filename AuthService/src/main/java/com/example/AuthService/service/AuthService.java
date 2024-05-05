package com.example.AuthService.service;

import com.example.AuthService.domain.dto.inDTO.LoginInDTO;
import com.example.AuthService.domain.dto.inDTO.UserInDTO;
import com.example.AuthService.domain.dto.outDTO.BaseOutDTO;
import com.example.AuthService.domain.dto.outDTO.LoginOutDTO;
import com.example.AuthService.domain.dto.outDTO.UserOutDTO;

public interface AuthService {
    UserOutDTO signUp(UserInDTO inDTO);
    LoginOutDTO login(LoginInDTO inDTO);
    BaseOutDTO logout(Long userId);

}
