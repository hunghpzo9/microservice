package com.example.AuthService.controller;

import com.example.AuthService.domain.dto.inDTO.LoginInDTO;
import com.example.AuthService.domain.dto.inDTO.UserInDTO;
import com.example.AuthService.domain.dto.outDTO.*;
import com.example.AuthService.domain.dto.UserDTO;
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
    public ResponseEntity<?> login(@RequestBody LoginInDTO inDTO) {

        LoginOutDTO outDTO = authService.login(inDTO);
        return new ResponseEntityOutDTO(outDTO);
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(Const.API_HEADER.CLIENT_ID) Long userId) {

        BaseOutDTO outDTO = authService.logout(userId);
        return new ResponseEntityOutDTO(outDTO);
    }
    @GetMapping("/handlerRefreshToken")
    public ResponseEntity<?> handlerRefreshToken(@RequestHeader(Const.API_HEADER.CLIENT_ID) Long userId,
                                                 @RequestHeader(Const.API_HEADER.REFRESH_TOKEN) String refreshToken) {

        TokenOutDTO outDTO = authService.handlerRefreshToken(userId,refreshToken);
        return new ResponseEntityOutDTO(outDTO);
    }
    @GetMapping("/authentication")
    public ResponseEntity<?> authentication(@RequestHeader(Const.API_HEADER.CLIENT_ID) Long userId,
                                                 @RequestHeader(Const.API_HEADER.AUTHORIZATION) String accessToken) {

        BaseOutDTO outDTO = authService.authentication(userId,accessToken);
        return new ResponseEntityOutDTO(outDTO);
    }
}
