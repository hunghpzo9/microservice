package com.example.AuthService.controller;

import com.example.AuthService.domain.dto.inDTO.AuthenticationDTO;
import com.example.AuthService.domain.dto.inDTO.BaseRequestDTO;
import com.example.AuthService.domain.dto.inDTO.LoginInDTO;
import com.example.AuthService.domain.dto.inDTO.UserInDTO;
import com.example.AuthService.domain.dto.outDTO.*;
import com.example.AuthService.domain.dto.UserDTO;
import com.example.AuthService.service.AuthService;
import com.example.AuthService.service.RedisService;
import com.example.AuthService.service.UserService;
import com.example.AuthService.utils.*;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xerial.snappy.Snappy;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/controller")
@Slf4j
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    RedisService redisService;
    @Autowired
    private UserService userService;

    @PostMapping("/testKeyRedis")
    public ResponseEntity<?> testKeyRedis() {

        Date current = new Date();
        log.info("Start testKeyRedis: "+current);
        ExecutorService executorService = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 80000; i++) {
            executorService.submit(() -> {
                Long id = IdGenerator.nextId();
                redisService.setNx(String.valueOf(id), id, 30000L, TimeUnit.MILLISECONDS);
            });
        }

        // Shut down the executor service properly
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("Executor service did not terminate");
                }
            }
        } catch (InterruptedException ex) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
        log.info("End testKeyRedis: "+(new Date().getTime()-current.getTime()));
        return null;
    }
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
    public ResponseEntity<?> logout(@RequestBody BaseRequestDTO dto) {

        BaseOutDTO outDTO = authService.logout(dto);
        return new ResponseEntityOutDTO(outDTO);
    }
    @PostMapping("/handlerRefreshToken")
    public ResponseEntity<?> handlerRefreshToken(@RequestBody AuthenticationDTO dto) {

        TokenOutDTO outDTO = authService.handlerRefreshToken(dto);
        return new ResponseEntityOutDTO(outDTO);
    }
    @PostMapping("/authentication")
    public ResponseEntity<?> authentication(@RequestBody AuthenticationDTO dto) {

        BaseOutDTO outDTO = authService.authentication(dto);
        return new ResponseEntityOutDTO(outDTO);
    }
    @PostMapping("/generateFakeData")
    public ResponseEntity<?> generateFakeData() {

        BaseOutDTO outDTO = new BaseOutDTO();
        String content = DataUtils.generateMD5("{\"email\":\"hun2g@gmail.com\",\"password\":\"Hung@951230\"}\n");
        String requestUri = "authService/generateFakeData";

        //Generate nonce với sessionID và random number vì hacker đứng chính giữa có thể là sessionID khác
        String nonce = NonceUtil.generateNonce(SessionUtil.generateSessionID());
        String stringToSign = content + "\n" + requestUri +"\n" + nonce;

        JsonObject json = new JsonObject();
        json.addProperty("nonce", nonce);
        json.addProperty("content", content);
        json.addProperty("signature", DataUtils.generateSignature(Const.SECRET_KEY,stringToSign));

        outDTO.setResponseSuccess("Ok",json.toString());
        userService.getUserInfo(1247591471344500736L);
        return new ResponseEntityOutDTO(outDTO);
    }
}
