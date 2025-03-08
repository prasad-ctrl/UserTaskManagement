package com.ecommers.multiapprovalsystem.Service;

import com.ecommers.multiapprovalsystem.DTO.AuthenticationResponse;
import com.ecommers.multiapprovalsystem.DTO.RegisterRequest;
import com.ecommers.multiapprovalsystem.DTO.RegisterResponse;
import com.ecommers.multiapprovalsystem.Model.Login;
import com.ecommers.multiapprovalsystem.Model.User;
import com.ecommers.multiapprovalsystem.Repository.LoginRepo;
import com.ecommers.multiapprovalsystem.Repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private LoginRepo loginRepo;
    @Override
    public AuthenticationResponse authentication(String email, String password){
        if(email.isBlank() || password.isBlank()){
            log.error("Authentication Required username and password");
            return null;
        }
        com.ecommers.multiapprovalsystem.Model.User user = userRepo.findByEmail(email);
        if(user != null && email.equalsIgnoreCase(user.getEmail()) && checkPassword(password,user.getPassword())){
            Login loggedInUser = loginRepo.findByUserId(user.getId());
            if(loggedInUser != null)
                return AuthenticationResponse.builder().userId(loggedInUser.getUserId()).sessionId(loggedInUser.getLoginId()).build();
            String loginId = UUID.randomUUID().toString();
            Login newLoginRequest = Login.builder()
                    .userId(user.getId())
                    .loginId(loginId)
                    .build();
            loginRepo.save(newLoginRequest);
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
