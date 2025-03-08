package com.ecommers.multiapprovalsystem.Service;


import com.ecommers.multiapprovalsystem.DTO.RegisterRequest;


public interface AuthenticationService {
    public String authentication(String username, String password) throws Exception;

    public boolean registerUser(RegisterRequest request);

}
