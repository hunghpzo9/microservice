package com.example.AuthService.service.impl;

import com.example.AuthService.domain.KeyToken;
import com.example.AuthService.domain.User;
import com.example.AuthService.domain.dto.KeyTokenDTO;
import com.example.AuthService.domain.dto.UserDTO;
import com.example.AuthService.repository.KeyTokenRepository;
import com.example.AuthService.repository.UserRepository;
import com.example.AuthService.service.JWTService;
import com.example.AuthService.service.KeyTokenService;
import com.example.AuthService.service.RedisService;
import com.example.AuthService.service.UserService;
import com.example.AuthService.utils.CompressionUtil;
import com.example.AuthService.utils.Const;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xerial.snappy.Snappy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private KeyTokenRepository keyTokenRepository;
    @Autowired
    private RedisService redisService;

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

    @Override
    public UserDTO getUserInfo(Long userId) {
        UserDTO userDTO = null;
        try {
            String userInfo = redisService.hget("user:"+userId,Const.REDIS_FIELD.USER_INFO);
            if(!StringUtil.isEmpty(userInfo)){
                ObjectMapper objectMapper = new ObjectMapper();
                userDTO = new UserDTO();
                userDTO = objectMapper.readValue(userInfo,UserDTO.class);

                String keyTokenInfo = redisService.hget("user:"+userId,Const.REDIS_FIELD.KEY_TOKEN);
                if(!StringUtil.isEmpty(keyTokenInfo)){
                    KeyToken keyToken = objectMapper.readValue(keyTokenInfo,KeyToken.class);
                    userDTO.setKeyTokenPublicKey(keyToken.getPublicKey());
                    userDTO.setRefreshToken(keyToken.getRefreshToken());
                }
                return userDTO;
            }

            Optional<User> optional = userRepository.findById(userId);
            if (optional.isEmpty()) {
                return null;
            }

            User user = optional.get();
            if(!Const.Status.ACTIVE.name().equals(user.getStatus())){
                return null;
            }
            Gson gson = new Gson();
            Map<String,String> map = new HashMap<>();

            userDTO = new UserDTO();
            userDTO.setId(userId);
            userDTO.setUserName(user.getUserName());
            userDTO.setEmail(user.getEmail());
            userDTO.setPassword(user.getPassword());
            userDTO.setStatus(user.getStatus());
            map.put(Const.REDIS_FIELD.USER_INFO,gson.toJson(userDTO));

            List<KeyTokenDTO> keyToken = keyTokenRepository.findKeyTokenByUserId(userId,Const.Status.ACTIVE.name());
            if(!keyToken.isEmpty()){
                KeyToken keyToken1 = new KeyToken();
                keyToken1.setPublicKey(keyToken.get(0).getPublicKey());
                keyToken1.setRefreshToken(keyToken.get(0).getRefreshToken());
                map.put(Const.REDIS_FIELD.KEY_TOKEN,gson.toJson(keyToken1));
                userDTO.setKeyTokenPublicKey(keyToken.get(0).getPublicKey());
                userDTO.setRefreshToken(keyToken.get(0).getRefreshToken());
            }
            redisService.hset("user:"+userId,map,600L, TimeUnit.SECONDS);

        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return userDTO;
    }
}
