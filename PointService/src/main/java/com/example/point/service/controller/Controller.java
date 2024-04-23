package com.example.point.service.controller;


import com.example.point.service.domain.dto.AccountPointDTO;
import com.example.point.service.domain.dto.BaseOutDTO;
import com.example.point.service.service.AccountPointService;
import com.example.point.service.service.RedisService;
import com.example.point.service.service.UserService;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/controller")

public class Controller {
    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;
    @Autowired
    AccountPointService accountPointService;

    @GetMapping("/getRequestIp")
    public ResponseEntity<?> request() {
        return ResponseEntity.ok("docker run 8083:8083");
    }

    @GetMapping("/getUser")
    public BaseOutDTO getUser() {
        BaseOutDTO outDTO = new BaseOutDTO();

        int maxTime = 10;
        int time = 1;
        String key = "iphone13";

        if (redisService.setNx(key)) {
            redisService.setKey(key, 0);
        }
        long currentTime = redisService.incrBy(key,time);
        if(currentTime > maxTime){
            System.out.println("Het hang roi");
            outDTO.setMessage("Het hang roi");
            return outDTO;
        }
        System.out.println("So luong ban ra: "+currentTime);
        if(currentTime > maxTime){
            redisService.setKey("ban qua hang", currentTime-maxTime);
        }
        outDTO.setMessage("Thanh cong");
        return outDTO;
    }

    @PostMapping("/adjustAccountPoint")
    public ResponseEntity<?> adjustAccountPoint(@RequestBody AccountPointDTO dto) {
        BaseOutDTO outDTO = accountPointService.adjustAccountPoint(dto);
        return new ResponseEntity(outDTO, HttpStatus.OK);
    }

}
