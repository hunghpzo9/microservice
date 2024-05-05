package com.example.AuthService.controller;

import com.example.AuthService.domain.dto.inDTO.LoginInDTO;
import com.example.AuthService.domain.dto.inDTO.UserInDTO;
import com.example.AuthService.domain.dto.outDTO.BaseOutDTO;
import com.example.AuthService.domain.dto.UserDTO;
import com.example.AuthService.domain.dto.outDTO.LoginOutDTO;
import com.example.AuthService.domain.dto.outDTO.ResponseEntityOutDTO;
import com.example.AuthService.domain.dto.outDTO.UserOutDTO;
import com.example.AuthService.service.AuthService;
import com.example.AuthService.service.UserService;
import com.example.AuthService.utils.Const;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/controller")
@Slf4j
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserInDTO inDTO) {

        UserOutDTO outDTO = authService.signUp(inDTO);
        return new ResponseEntityOutDTO(outDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginInDTO inDTO) {

        LoginOutDTO outDTO = authService.login(inDTO);
        return new ResponseEntityOutDTO(outDTO);
    }
}
