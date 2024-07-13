package com.example.AuthService.controller;

import com.example.AuthService.domain.dto.inDTO.AuthenticationDTO;
import com.example.AuthService.domain.dto.inDTO.LoginInDTO;
import com.example.AuthService.domain.dto.inDTO.UserInDTO;
import com.example.AuthService.domain.dto.outDTO.*;
import com.example.AuthService.domain.dto.UserDTO;
import com.example.AuthService.service.AuthService;
import com.example.AuthService.service.UserService;
import com.example.AuthService.utils.Const;
import com.example.AuthService.utils.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
    @PostMapping("/handlerRefreshToken")
    public ResponseEntity<?> handlerRefreshToken(AuthenticationDTO dto) {

        TokenOutDTO outDTO = authService.handlerRefreshToken(dto);
        return new ResponseEntityOutDTO(outDTO);
    }
    @PostMapping("/authentication")
    public ResponseEntity<?> authentication(AuthenticationDTO dto) {

        BaseOutDTO outDTO = authService.authentication(dto);
        return new ResponseEntityOutDTO(outDTO);
    }
    @PostMapping("/generateFakeData")
    public ResponseEntity<?> generateFakeData(AuthenticationDTO dto) {

        BaseOutDTO outDTO = new BaseOutDTO();
        Date date = new Date();
        String content = DataUtils.generateMD5("{\"email\":\"hun2g@gmail.com\",\"password\":\"Hung@951230\"}\n");
        String requestUri = "authService/generateFakeData";
        String nonce = DataUtils.generateNonce("1247591471344500736",date.getTime());
        String stringToSign = content + "\n" + requestUri +"\n" + nonce;
        outDTO.setResponseSuccess("Ok",DataUtils.generateSignature("key",stringToSign));
        return new ResponseEntityOutDTO(outDTO);
    }
}
