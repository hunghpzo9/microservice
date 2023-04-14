package com.example.point.service.service;

import com.example.point.service.domain.AccountPoint;
import com.example.point.service.domain.UserAccount;
import com.example.point.service.domain.dto.AccountPointDTO;
import com.example.point.service.domain.dto.BaseOutDTO;
import com.example.point.service.domain.dto.UserAccountDTO;
import com.example.point.service.exception.RedeemPointFailedException;
import com.example.point.service.repository.AccountPointRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
public class AccountPointServiceServiceImpl implements AccountPointService {


    @Autowired
    AdjustAccountPointService adjustAccountPointService;

    @Override
    public BaseOutDTO adjustAccountPoint(AccountPointDTO dto)  {
        BaseOutDTO outDTO = new BaseOutDTO();
        try {
            if (dto.getPointValue() == null || dto.getUserAccountId() == null) {
                outDTO.setCode("004");
                outDTO.setMessage("Invalid data");
                return outDTO;
            }
            outDTO = adjustAccountPointService.adjustPoint(dto);
        } catch (Exception ex) {
            if (ex instanceof RedeemPointFailedException) {
                outDTO.setCode("005");
                outDTO.setMessage("RedeemPointFailedException");
            } else {
                outDTO.setCode("004");
                outDTO.setMessage("Failed");
            }
        }
        return outDTO;

    }


}
