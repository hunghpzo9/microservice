package com.example.AuthService.service.impl;

import com.example.AuthService.domain.KeyToken;
import com.example.AuthService.domain.RefreshTokenUsed;
import com.example.AuthService.domain.dto.KeyPairDTO;
import com.example.AuthService.domain.dto.KeyTokenDTO;
import com.example.AuthService.domain.dto.RefreshTokenUsedDTO;
import com.example.AuthService.domain.dto.UserDTO;
import com.example.AuthService.domain.dto.outDTO.BaseOutDTO;
import com.example.AuthService.repository.KeyTokenRepository;
import com.example.AuthService.repository.RefreshTokenUsedRepository;
import com.example.AuthService.service.KeyTokenService;
import com.example.AuthService.service.RedisService;
import com.example.AuthService.service.UserService;
import com.example.AuthService.utils.Const;
import com.example.AuthService.utils.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class KeyTokenServiceImpl implements KeyTokenService {
    @Autowired
    private KeyTokenRepository keyTokenRepository;
    @Autowired
    private RedisService redisService;

    @Override
    public BaseOutDTO createNewKeyToken(KeyPairDTO keyPair, Long userId, String refreshToken) {
        BaseOutDTO outDTO = new BaseOutDTO();
        try {
            if (userId == null) {
                log.info("createNewKeyToken|userId is null or empty");
                outDTO.setResponseBadRequest(Const.RESPONSE_CODE.DATA_INVALID, Const.RESPONSE_MESSAGE.DATA_INVALID);
                return outDTO;
            }
            if (keyPair == null ||
                    DataUtils.isNullOrEmpty(keyPair.getPublicKey()) ||
                    DataUtils.isNullOrEmpty(keyPair.getPrivateKey())) {
                log.info("createNewKeyToken|KeyPair is null");
                outDTO.setResponseBadRequest(Const.RESPONSE_CODE.DATA_INVALID, Const.RESPONSE_MESSAGE.DATA_INVALID);
                return outDTO;
            }

            KeyTokenDTO keyTokenDTO = new KeyTokenDTO();
            List<KeyToken> keyTokenList = keyTokenRepository.findByUserId(userId);
            if (!keyTokenList.isEmpty()) {
                keyTokenDTO.setId(keyTokenList.get(0).getId());
                keyTokenDTO.setCreateDate(keyTokenList.get(0).getCreateDate());
            }

            keyTokenDTO.setUserId(userId);
            keyTokenDTO.setPublicKey(keyPair.getPublicKey());
            keyTokenDTO.setPrivateKey(keyPair.getPrivateKey());
            keyTokenDTO.setRefreshToken(refreshToken);
            keyTokenDTO.setStatus(Const.Status.ACTIVE.name());

            keyTokenRepository.save(new KeyToken(keyTokenDTO));
            redisService.deleteValue("user:"+userId);

            outDTO.setResponseSuccess(Const.RESPONSE_CODE.SUCCESS, Const.RESPONSE_MESSAGE.SUCCESS);
        } catch (Exception e) {
            outDTO.setResponseInternalServerError(Const.RESPONSE_CODE.ERROR, Const.RESPONSE_MESSAGE.ERROR);
        log.error(e.getMessage(),e);
        }
        return outDTO;
    }

    @Override
    public BaseOutDTO deleteAllKeyToken(Long userId) {
        BaseOutDTO outDTO = new BaseOutDTO();
        try {
            List<KeyToken> keyTokenList = keyTokenRepository.findByUserId(userId);
            if (!keyTokenList.isEmpty()) {
                keyTokenRepository.deleteAll(keyTokenList);
            }
            outDTO.setResponseSuccess(Const.RESPONSE_CODE.SUCCESS, Const.RESPONSE_MESSAGE.SUCCESS);
        } catch (Exception e) {
            outDTO.setResponseInternalServerError(Const.RESPONSE_CODE.ERROR, Const.RESPONSE_MESSAGE.ERROR);
            log.error(e.getMessage(),e);
        }
        return outDTO;
    }
}
