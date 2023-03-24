package com.example.point.service.service;

import com.example.point.service.domain.UserAccount;
import com.example.point.service.domain.dto.BaseOutDTO;
import com.example.point.service.domain.dto.UserAccountDTO;
import com.example.point.service.domain.dto.outDto.UserAccountOutDTO;
import com.example.point.service.repository.UserAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private UserAccountRepository userAccountRepository;

    public UserServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }
    @Override
    public BaseOutDTO getAllUser() {
        UserAccountOutDTO outDTO = new UserAccountOutDTO();
        try {
            List<UserAccount> accounts = userAccountRepository.findAll();
            outDTO.setCode("000");
            outDTO.setMessage("Ok");
            outDTO.setDtoList(accounts);
        } catch (Exception ex) {
            outDTO.setCode("001");
            outDTO.setMessage("Error");
        }

        return outDTO;
    }



}
