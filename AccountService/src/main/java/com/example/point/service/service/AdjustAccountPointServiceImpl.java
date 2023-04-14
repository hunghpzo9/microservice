package com.example.point.service.service;

import com.example.point.service.domain.AccountPoint;
import com.example.point.service.domain.dto.AccountPointDTO;
import com.example.point.service.domain.dto.BaseOutDTO;
import com.example.point.service.exception.RedeemPointFailedException;
import com.example.point.service.repository.AccountPointRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
public class AdjustAccountPointServiceImpl implements AdjustAccountPointService {
    @Autowired
    AccountPointRepository accountPointRepository;

    @Override
    @Transactional(rollbackFor = RedeemPointFailedException.class)
    public BaseOutDTO adjustPoint(AccountPointDTO dto) throws Exception {
        BaseOutDTO outDTO = new BaseOutDTO();
        dto.setPointExpireDate(new Date());
        accountPointRepository.save(new AccountPoint(dto));
        log.info("Save successfully");
        int x = getResult();
        if (x == 0) {
            outDTO.setCode("000");
            outDTO.setMessage("OK");
        } else if (x == 1) {
            throw new RedeemPointFailedException("error");
        } else {
            outDTO.setCode("004");
            outDTO.setMessage("Failed");
        }
        return outDTO;

    }

    private int getResult() {
        int result = 0;
        try {
            int x = 0;
            if (x % 2 == 1) {
                result = 0;
            } else {
                result = 1;
            }
        } catch (Exception ex) {
            result = 1;
            return result;
        }
        return result;
    }
}
