package com.example.AuthService.service.impl;

import com.example.AuthService.domain.KeyToken;
import com.example.AuthService.domain.RefreshTokenUsed;
import com.example.AuthService.domain.dto.RefreshTokenUsedDTO;
import com.example.AuthService.domain.dto.outDTO.BaseOutDTO;
import com.example.AuthService.repository.RefreshTokenUsedRepository;
import com.example.AuthService.service.RefreshTokenUsedService;
import com.example.AuthService.utils.Const;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RefreshTokenUsedServiceImpl implements RefreshTokenUsedService {
    @Autowired
    private RefreshTokenUsedRepository refreshTokenUsedRepository;
    @Override
    public BaseOutDTO addNewUsedToken(Long userId, String refreshToken) {
        BaseOutDTO outDTO = new BaseOutDTO();
        try{

        RefreshTokenUsedDTO refreshTokenUsedDTO = new RefreshTokenUsedDTO();
        refreshTokenUsedDTO.setStatus(Const.Status.ACTIVE.name());
        refreshTokenUsedDTO.setUserId(userId);
        refreshTokenUsedDTO.setToken(refreshToken);
        refreshTokenUsedRepository.save(new RefreshTokenUsed(refreshTokenUsedDTO));
            outDTO.setResponseSuccess(Const.RESPONSE_CODE.SUCCESS, Const.RESPONSE_MESSAGE.SUCCESS);
    } catch (Exception e) {
            log.error(e.getMessage(),e);
        outDTO.setResponseInternalServerError(Const.RESPONSE_CODE.ERROR, Const.RESPONSE_MESSAGE.ERROR);
    }
        return null;
    }

    @Override
    public BaseOutDTO deleteAllUsedToken(Long userId) {
        BaseOutDTO outDTO = new BaseOutDTO();
        try {
            List<RefreshTokenUsed> refreshTokenUsedList = refreshTokenUsedRepository.findByUserId(userId);
            if (!refreshTokenUsedList.isEmpty()) {
                refreshTokenUsedRepository.deleteAll(refreshTokenUsedList);
            }
            outDTO.setResponseSuccess(Const.RESPONSE_CODE.SUCCESS, Const.RESPONSE_MESSAGE.SUCCESS);
        } catch (Exception e) {
            outDTO.setResponseInternalServerError(Const.RESPONSE_CODE.ERROR, Const.RESPONSE_MESSAGE.ERROR);
            log.error(e.getMessage(),e);
        }
        return outDTO;
    }

}
