package com.example.point.service.service;

import com.example.point.service.domain.dto.AccountPointDTO;
import com.example.point.service.domain.dto.BaseOutDTO;

public interface AdjustAccountPointService {
    BaseOutDTO adjustPoint(AccountPointDTO dto) throws Exception;

}
