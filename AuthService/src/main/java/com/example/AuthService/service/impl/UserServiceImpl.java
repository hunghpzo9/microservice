package com.example.AuthService.service.impl;

import com.example.AuthService.domain.User;
import com.example.AuthService.domain.dto.KeyPairDTO;
import com.example.AuthService.domain.dto.inDTO.UserInDTO;
import com.example.AuthService.domain.dto.UserDTO;
import com.example.AuthService.domain.dto.outDTO.UserOutDTO;
import com.example.AuthService.repository.UserRepository;
import com.example.AuthService.service.JWTService;
import com.example.AuthService.service.KeyTokenService;
import com.example.AuthService.service.UserService;
import com.example.AuthService.utils.Const;
import com.example.AuthService.utils.DataUtils;
import com.example.AuthService.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
            if (userDTOList.size() != 0) {
                userDTO = userDTOList.get(0);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return userDTO;
    }

    @Override
    public UserOutDTO signUp(UserInDTO inDTO) {
        UserOutDTO outDTO = new UserOutDTO();
        try {
            if (DataUtils.isNullOrEmpty(inDTO.getEmail()) ||
                    DataUtils.isNullOrEmpty(inDTO.getPassword()) ||
                    DataUtils.isNullOrEmpty(inDTO.getUserName())) {
                outDTO.setResponseBadRequest(Const.RESPONSE_CODE.DATA_INVALID,Const.RESPONSE_MESSAGE.DATA_INVALID);
                return outDTO;
            }

            String email = inDTO.getEmail().trim().toString();
            String userName = inDTO.getUserName().trim().toString();
            String password = inDTO.getPassword().trim().toString();

            if (!EmailValidator.getInstance().isValid(email)) {
                outDTO.setResponseBadRequest(Const.RESPONSE_CODE.EMAIL_INVALID,Const.RESPONSE_MESSAGE.EMAIL_INVALID);
                return outDTO;
            }
            if (!isPasswordStrong(password, userName, email)) {
                outDTO.setResponseBadRequest(Const.RESPONSE_CODE.PASSWORD_WEAK,Const.RESPONSE_MESSAGE.PASSWORD_WEAK);
                outDTO.setMessage(Const.RESPONSE_MESSAGE.PASSWORD_WEAK);
                return outDTO;
            }

            if (findUserByEmail(email, Const.Status.ACTIVE.name()) != null) {
                outDTO.setResponseBadRequest(Const.RESPONSE_CODE.EMAIL_EXISTED,Const.RESPONSE_MESSAGE.EMAIL_EXISTED);
                return outDTO;
            }

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            Long userId = IdGenerator.nextId();
            UserDTO userDTO = new UserDTO();
            userDTO.setId(userId);
            userDTO.setUserName(userName);
            userDTO.setEmail(email);
            userDTO.setPassword(passwordEncoder.encode(password));
            userDTO.setStatus(Const.Status.ACTIVE.name());
            userRepository.save(new User(userDTO));

            KeyPairDTO keyPair = keyTokenService.getKeyPair();
            if (keyPair == null) {
                outDTO.setCode(Const.RESPONSE_CODE.ERROR);
                outDTO.setMessage(Const.RESPONSE_MESSAGE.ERROR);
                return outDTO;
            }

            keyTokenService.createNewKeyToken(keyPair,userId);
//            String accessToken = jwtService.generateToken(userId.toString(),keyPair, Const.TOKEN.ACCESS_TOKEN);
//            String refreshToken = jwtService.generateToken(userId.toString(),keyPair,Const.TOKEN.REFRESH_TOKEN);

//            if(DataUtils.isNullOrEmpty(accessToken) || DataUtils.isNullOrEmpty(refreshToken)){
//                outDTO.setCode(Const.RESPONSE_CODE.ERROR);
//                outDTO.setMessage(Const.RESPONSE_MESSAGE.ERROR);
//                return outDTO;
//            }

            List<UserDTO> userDTOList = new ArrayList<>();
            userDTOList.add(userDTO);

            outDTO.setUserDTOList(userDTOList);
            outDTO.setResponseCreated(Const.RESPONSE_CODE.CREATED,Const.RESPONSE_MESSAGE.SIGN_UP_SUCCESS);
        } catch (Exception e) {
            outDTO.setResponseInternalServerError(Const.RESPONSE_CODE.ERROR,Const.RESPONSE_MESSAGE.ERROR);
            log.error(e.getMessage());
        }
        return outDTO;
    }

    private boolean isPasswordStrong(String password, String userName, String email) {
        if (!Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")
                .matcher(password)
                .find()) {
            return false;
        }
        if (password.equals(userName) || password.equals(email)) {
            return false;
        }
        //Also can check password equal domain, appname,
        return true;
    }


}
