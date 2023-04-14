package com.example.point.service.controller;



import com.example.point.service.domain.dto.AccountPointDTO;
import com.example.point.service.domain.dto.BaseOutDTO;
import com.example.point.service.service.AccountPointService;
import com.example.point.service.service.UserService;
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
    AccountPointService accountPointService;

    @GetMapping("/getRequestIp")
    public ResponseEntity<?> request() {
        return ResponseEntity.ok("docker run 8083:8083");
    }

    @GetMapping("/getUser")
    public BaseOutDTO getUser() {
        BaseOutDTO outDTO = userService.getAllUser();
        return outDTO;
    }

    @PostMapping("/adjustAccountPoint")
    public ResponseEntity<?> adjustAccountPoint(@RequestBody AccountPointDTO dto) {
        BaseOutDTO outDTO= accountPointService.adjustAccountPoint(dto);
        return new ResponseEntity(outDTO,HttpStatus.OK);
    }

}
