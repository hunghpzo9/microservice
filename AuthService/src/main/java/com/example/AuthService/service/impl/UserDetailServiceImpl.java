package com.example.AuthService.service.impl;

import com.example.AuthService.domain.CustomUserDetails;
import com.example.AuthService.domain.dto.UserDTO;
import com.example.AuthService.domain.dto.outDTO.UserOutDTO;
import com.example.AuthService.service.UserService;
import com.example.AuthService.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = userService.findUserByEmail(username, Const.Status.ACTIVE.name());
        if (user!= null) {
            return new CustomUserDetails(user);
        }
        throw new UsernameNotFoundException("user not available");
    }
}
