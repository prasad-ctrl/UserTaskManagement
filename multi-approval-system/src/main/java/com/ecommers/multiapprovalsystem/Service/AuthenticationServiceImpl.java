package com.ecommers.multiapprovalsystem.Service;

import com.ecommers.multiapprovalsystem.DTO.RegisterRequest;
import com.ecommers.multiapprovalsystem.Model.Login;
import com.ecommers.multiapprovalsystem.Model.User;
import com.ecommers.multiapprovalsystem.Repository.LoginRepo;
import com.ecommers.multiapprovalsystem.Repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
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
    public String authentication(String email, String password) throws Exception {
        if(email.isBlank() || password.isBlank()){
            throw new Exception("username and password must not be empty");
        }
        com.ecommers.multiapprovalsystem.Model.User user = userRepo.findByEmail(email);
        if(user != null && email.equalsIgnoreCase(user.getEmail()) && password.equalsIgnoreCase(user.getPassword())){
            Login loggedInUser = loginRepo.findByUserId(user.getId());
            if(loggedInUser != null)return loggedInUser.getLoginId();
            String loginId = UUID.randomUUID().toString();
            Login newLoginRequest = Login.builder()
                    .userId(user.getId())
                    .loginId(loginId)
                    .build();
            loginRepo.save(newLoginRequest);
            return loginId;
        }
        return null;
    }

    @Override
    public boolean registerUser(RegisterRequest request) {
        try {
            validateRegistration(request);
            User registerUser = User.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .name(request.getName())
                    .build();
            userRepo.save(registerUser);
            return true;
        }catch (Exception e){
            log.error("Error registering user Error: {}",e.getMessage());
        }
        return false;
    }
    public void validateRegistration(RegisterRequest request) throws Exception{
        if(request.getEmail().isBlank() || request.getPassword().isBlank() || request.getName().isBlank())
            throw new Exception("Invalid Request");
    }
}
