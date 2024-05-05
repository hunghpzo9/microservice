package com.example.AuthService.service.impl;

import com.example.AuthService.domain.dto.UserDTO;
import com.example.AuthService.repository.UserRepository;
import com.example.AuthService.service.JWTService;
import com.example.AuthService.service.KeyTokenService;
import com.example.AuthService.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private KeyTokenService keyTokenService;
    @Autowired
    private JWTService jwtService;

    @Override
    public UserDTO findUserByEmail(String email, String status) {
        UserDTO userDTO = null;
        try {

            List<UserDTO> userDTOList = userRepository.findUserByEmail(email, status);
            if (!userDTOList.isEmpty()) {
                userDTO = userDTOList.get(0);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return userDTO;
    }
}
