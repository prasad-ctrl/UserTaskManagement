package com.ecommers.multiapprovalsystem.Service.Impl;

import com.ecommers.multiapprovalsystem.Component.JwtUtil;
import com.ecommers.multiapprovalsystem.DTO.AuthenticationResponse;
import com.ecommers.multiapprovalsystem.DTO.RegisterRequest;
import com.ecommers.multiapprovalsystem.DTO.RegisterResponse;
import com.ecommers.multiapprovalsystem.Model.Token;
import com.ecommers.multiapprovalsystem.Model.User;
import com.ecommers.multiapprovalsystem.Repository.TokenRepo;
import com.ecommers.multiapprovalsystem.Repository.UserRepo;
import com.ecommers.multiapprovalsystem.Service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenRepo loginRepo;
    @Override
    public AuthenticationResponse authentication(String email, String password){
        if(email.isBlank() || password.isBlank()){
            log.error("Authentication Required username and password");
            return null;
        }
        com.ecommers.multiapprovalsystem.Model.User user = userRepo.findByEmail(email);
        if(user != null && email.equalsIgnoreCase(user.getEmail()) && checkPassword(password,user.getPassword())){
            String loginId = jwtUtil.generateToken(email);
            Token token  = Token.builder()
                    .loginId(loginId)
                    .userId(user.getId())
                    .expiryDate(new Date(System.currentTimeMillis() + 60 * 60 * 1000)).build();
            loginRepo.save(token);
            return AuthenticationResponse.builder().userId(user.getId()).sessionId(loginId).build();
        }
        return null;
    }

    @Override
    public RegisterResponse registerUser(RegisterRequest request) {
        RegisterResponse response;
        try {
            validateRegistration(request);
            User registerUser = User.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(encryptPassword(request.getPassword()))
                    .build();
            userRepo.save(registerUser);
             response = RegisterResponse.builder()
                    .userName(request.getName())
                    .message("Registered Successfully")
                    .build();

        }catch (Exception e){
            response = RegisterResponse.builder()
                    .userName(request.getName())
                    .message("Registered Failed!")
                    .build();
            log.error("Error registering user Error: {}",e.getMessage());
        }
        return response;
    }

    @Override
    public void logout(String tokenId) {
        jwtUtil.removeToken(tokenId);
    }

    public void validateRegistration(RegisterRequest request) throws Exception{
        if(request.getEmail().isBlank() || request.getPassword().isBlank() || request.getName().isBlank())
            throw new Exception("Invalid Request");
    }
    public String encryptPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);  // Verify password
    }
}
