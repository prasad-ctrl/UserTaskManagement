package com.ecommers.multiapprovalsystem.Service;


import com.ecommers.multiapprovalsystem.DTO.AuthenticationResponse;
import com.ecommers.multiapprovalsystem.DTO.RegisterRequest;
import com.ecommers.multiapprovalsystem.DTO.RegisterResponse;


public interface AuthenticationService {
    public AuthenticationResponse authentication(String username, String password) ;

    public RegisterResponse registerUser(RegisterRequest request);

}
