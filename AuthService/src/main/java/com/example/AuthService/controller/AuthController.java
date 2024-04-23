package com.example.AuthService.controller;

import com.example.AuthService.domain.dto.inDTO.UserInDTO;
import com.example.AuthService.domain.dto.outDTO.BaseOutDTO;
import com.example.AuthService.domain.dto.UserDTO;
import com.example.AuthService.domain.dto.outDTO.ResponseEntityOutDTO;
import com.example.AuthService.domain.dto.outDTO.UserOutDTO;
import com.example.AuthService.service.UserService;
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
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserInDTO inDTO) {

        UserOutDTO outDTO = userService.signUp(inDTO);
        return new ResponseEntityOutDTO(outDTO);
    }
}
