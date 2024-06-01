package com.example.AuthService.service.impl;

import com.example.AuthService.domain.RefreshTokenUsed;
import com.example.AuthService.domain.User;
import com.example.AuthService.domain.dto.*;
import com.example.AuthService.domain.dto.inDTO.LoginInDTO;
import com.example.AuthService.domain.dto.inDTO.UserInDTO;
import com.example.AuthService.domain.dto.outDTO.BaseOutDTO;
import com.example.AuthService.domain.dto.outDTO.LoginOutDTO;
import com.example.AuthService.domain.dto.outDTO.TokenOutDTO;
import com.example.AuthService.domain.dto.outDTO.UserOutDTO;
import com.example.AuthService.repository.KeyTokenRepository;
import com.example.AuthService.repository.RefreshTokenUsedRepository;
import com.example.AuthService.repository.UserRepository;
import com.example.AuthService.service.*;
import com.example.AuthService.utils.Const;
import com.example.AuthService.utils.DataUtils;
import com.example.AuthService.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.tokens.KeyToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private KeyTokenService keyTokenService;
    @Autowired
    private UserService userService;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private RefreshTokenUsedRepository refreshTokenUsedRepository;
    @Autowired
    private AsymmetricKeyService asymmetricKeyService;
    @Autowired
    private KeyTokenRepository keyTokenRepository;
    @Autowired
    private RefreshTokenUsedService refreshTokenUsedService;
    @Override
    public UserOutDTO signUp(UserInDTO inDTO) {
        UserOutDTO outDTO = new UserOutDTO();
        try {
            if (DataUtils.isNullOrEmpty(inDTO.getEmail()) ||
                    DataUtils.isNullOrEmpty(inDTO.getPassword()) ||
                    DataUtils.isNullOrEmpty(inDTO.getUserName())) {
                outDTO.setResponseBadRequest(Const.RESPONSE_CODE.DATA_INVALID, Const.RESPONSE_MESSAGE.DATA_INVALID);
                return outDTO;
            }

            String email = inDTO.getEmail().trim();
            String userName = inDTO.getUserName().trim();
            String password = inDTO.getPassword().trim();

            if (!EmailValidator.getInstance().isValid(email)) {
                outDTO.setResponseBadRequest(Const.RESPONSE_CODE.EMAIL_INVALID, Const.RESPONSE_MESSAGE.EMAIL_INVALID);
                return outDTO;
            }
            if (!Const.isPasswordStrong(password, userName, email)) {
                outDTO.setResponseBadRequest(Const.RESPONSE_CODE.PASSWORD_WEAK, Const.RESPONSE_MESSAGE.PASSWORD_WEAK);
                outDTO.setMessage(Const.RESPONSE_MESSAGE.PASSWORD_WEAK);
                return outDTO;
            }

            if (userService.findUserByEmail(email, Const.Status.ACTIVE.name()) != null) {
                outDTO.setResponseBadRequest(Const.RESPONSE_CODE.EMAIL_EXISTED, Const.RESPONSE_MESSAGE.EMAIL_EXISTED);
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

            KeyPairDTO keyPair = asymmetricKeyService.getKeyPair();
            if (keyPair == null) {
                outDTO.setResponseInternalServerError(Const.RESPONSE_CODE.ERROR, Const.RESPONSE_MESSAGE.ERROR);
                return outDTO;
            }

            keyTokenService.createNewKeyToken(keyPair, userId, null);

            List<UserDTO> userDTOList = new ArrayList<>();
            userDTOList.add(userDTO);

            outDTO.setUserDTOList(userDTOList);
            outDTO.setResponseCreated(Const.RESPONSE_CODE.CREATED, Const.RESPONSE_MESSAGE.SIGN_UP_SUCCESS);
        } catch (Exception e) {
            outDTO.setResponseInternalServerError(Const.RESPONSE_CODE.ERROR, Const.RESPONSE_MESSAGE.ERROR);
            log.error(e.getMessage());
        }
        return outDTO;
    }

    @Override
    public LoginOutDTO login(LoginInDTO inDTO) {
        LoginOutDTO outDTO = new LoginOutDTO();
        try {
            if (DataUtils.isNullOrEmpty(inDTO.getEmail()) ||
                    DataUtils.isNullOrEmpty(inDTO.getPassword())) {
                outDTO.setResponseBadRequest(Const.RESPONSE_CODE.DATA_INVALID, Const.RESPONSE_MESSAGE.DATA_INVALID);
                return outDTO;
            }

            String email = inDTO.getEmail().trim();
            String password = inDTO.getPassword().trim();

            UserDTO foundUser = userService.findUserByEmail(email, Const.Status.ACTIVE.name());
            if (foundUser == null) {
                outDTO.setResponseBadRequest(Const.RESPONSE_CODE.DATA_NOT_FOUND, Const.RESPONSE_MESSAGE.DATA_NOT_FOUND);
                return outDTO;
            }
            Long userId = foundUser.getId();

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (!passwordEncoder.matches(password, foundUser.getPassword())) {
                outDTO.setResponseAuthenticateFail(Const.RESPONSE_CODE.AUTHENTICATE_FAIL, Const.RESPONSE_MESSAGE.PASSWORD_INCORRECT);
                return outDTO;
            }

            KeyPairDTO keyPair = asymmetricKeyService.getKeyPair();
            if (keyPair == null) {
                outDTO.setResponseInternalServerError(Const.RESPONSE_CODE.ERROR, Const.RESPONSE_MESSAGE.ERROR);
            }

            String accessToken = jwtService.generateToken(userId.toString(), keyPair, Const.TOKEN.ACCESS_TOKEN);
            String refreshToken = jwtService.generateToken(userId.toString(), keyPair, Const.TOKEN.REFRESH_TOKEN);
            keyTokenService.createNewKeyToken(keyPair, foundUser.getId(), refreshToken);

            if (DataUtils.isNullOrEmpty(accessToken) || DataUtils.isNullOrEmpty(refreshToken)) {
                outDTO.setResponseInternalServerError(Const.RESPONSE_CODE.ERROR, Const.RESPONSE_MESSAGE.ERROR);
                return outDTO;
            }

            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setUserId(userId);
            loginDTO.setUserName(foundUser.getUserName());
            loginDTO.setEmail(email);
            loginDTO.setAccessToken(accessToken);
            loginDTO.setRefreshToken(refreshToken);
            List<LoginDTO> loginDTOList = new ArrayList<>();
            loginDTOList.add(loginDTO);

            outDTO.setResponseSuccess(Const.RESPONSE_CODE.SUCCESS, Const.RESPONSE_MESSAGE.SUCCESS);
            outDTO.setLoginDTOList(loginDTOList);

        } catch (Exception ex) {
            outDTO.setResponseInternalServerError(Const.RESPONSE_CODE.ERROR, Const.RESPONSE_MESSAGE.ERROR);
            log.error(ex.getMessage());
        }
        return outDTO;
    }

    @Override
    public BaseOutDTO logout(Long userId) {
        BaseOutDTO outDTO = new BaseOutDTO();
        try {
            if (userId == null) {
                outDTO.setResponseBadRequest(Const.RESPONSE_CODE.DATA_INVALID, Const.RESPONSE_MESSAGE.DATA_INVALID);
                return outDTO;
            }
            BaseOutDTO deleteKeyTokenOut = keyTokenService.deleteAllKeyToken(userId);
            BaseOutDTO deleteUsedTokenOut =  refreshTokenUsedService.deleteAllUsedToken(userId);
            if (!deleteKeyTokenOut.getCode().equals(Const.RESPONSE_CODE.SUCCESS)||
                    !deleteUsedTokenOut.getCode().equals(Const.RESPONSE_CODE.SUCCESS)) {
                outDTO.setResponseInternalServerError(Const.RESPONSE_CODE.ERROR, Const.RESPONSE_MESSAGE.ERROR);
            }
            outDTO.setResponseSuccess(Const.RESPONSE_CODE.SUCCESS, Const.RESPONSE_MESSAGE.SUCCESS);
        } catch (Exception ex) {
            outDTO.setResponseInternalServerError(Const.RESPONSE_CODE.ERROR, Const.RESPONSE_MESSAGE.ERROR);
            log.error(ex.getMessage());
        }
        return outDTO;
    }

    @Override
    public TokenOutDTO handlerRefreshToken(Long userId, String refreshToken) {
        TokenOutDTO outDTO = new TokenOutDTO();
        try {
            if (userId == null || DataUtils.isNullOrEmpty(refreshToken)) {
                outDTO.setResponseBadRequest(Const.RESPONSE_CODE.DATA_INVALID, Const.RESPONSE_MESSAGE.DATA_INVALID);
                return outDTO;
            }

            List<RefreshTokenUsed> list = refreshTokenUsedRepository.findByUserId(userId);
            if(list.stream().anyMatch(usedToken -> usedToken.getToken().equals(refreshToken))){
                outDTO.setResponseAuthenticateFail(Const.RESPONSE_CODE.AUTHENTICATE_FAIL, Const.RESPONSE_MESSAGE.DETECTED_USED_TOKEN);
                return outDTO;
            }

            List<KeyTokenDTO> keyToken = keyTokenRepository.findKeyTokenByUserId(userId,Const.Status.ACTIVE.name());
            if(keyToken.isEmpty()){
                outDTO.setResponseAuthenticateFail(Const.RESPONSE_CODE.AUTHENTICATE_FAIL, Const.RESPONSE_MESSAGE.DATA_NOT_FOUND);
                return outDTO;
            }

            Optional<User> user = userRepository.findById(userId);
            if(user.isEmpty()){
                outDTO.setResponseAuthenticateFail(Const.RESPONSE_CODE.AUTHENTICATE_FAIL, Const.RESPONSE_MESSAGE.DATA_NOT_FOUND);
                return outDTO;
            }

            KeyPairDTO keyPair = asymmetricKeyService.getKeyPair();
            if (keyPair == null) {
                outDTO.setResponseInternalServerError(Const.RESPONSE_CODE.ERROR, Const.RESPONSE_MESSAGE.ERROR);
            }

            String newAccessToken = jwtService.generateToken(userId.toString(), keyPair, Const.TOKEN.ACCESS_TOKEN);
            String newRefreshToken = jwtService.generateToken(userId.toString(), keyPair, Const.TOKEN.REFRESH_TOKEN);
            keyTokenService.createNewKeyToken(keyPair, userId, newRefreshToken);
            refreshTokenUsedService.addNewUsedToken(userId,refreshToken);

            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setAccessToken(newAccessToken);
            tokenDTO.setRefreshToken(newRefreshToken);

            outDTO.setTokenDTO(tokenDTO);
            outDTO.setResponseCreated(Const.RESPONSE_CODE.CREATED, Const.RESPONSE_MESSAGE.CREATED_TOKEN_OK);
        } catch (Exception ex) {
            outDTO.setResponseInternalServerError(Const.RESPONSE_CODE.ERROR, Const.RESPONSE_MESSAGE.ERROR);
            log.error(ex.getMessage());
        }
        return outDTO;
    }

    @Override
    public BaseOutDTO authentication(Long userId, String accessToken) {
        //authen here
        return null;
    }
}
