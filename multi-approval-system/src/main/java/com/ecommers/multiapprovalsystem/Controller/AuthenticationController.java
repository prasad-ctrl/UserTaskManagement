package com.ecommers.multiapprovalsystem.Controller;

import com.ecommers.multiapprovalsystem.DTO.AuthenticationResponse;
import com.ecommers.multiapprovalsystem.DTO.RegisterRequest;
import com.ecommers.multiapprovalsystem.DTO.RegisterResponse;
import com.ecommers.multiapprovalsystem.Service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@Slf4j
public class AuthenticationController {

    @Autowired
    public AuthenticationService service;

    @PostMapping("/auth")
    public ResponseEntity<AuthenticationResponse> login(@RequestParam String email,
                                                        @RequestParam String password){

        return new ResponseEntity<>(service.authentication(email, password), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request){
        return new ResponseEntity<>(service.registerUser(request), HttpStatus.OK);
    }
}
