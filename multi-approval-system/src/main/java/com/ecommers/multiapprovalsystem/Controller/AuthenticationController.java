package com.ecommers.multiapprovalsystem.Controller;

import com.ecommers.multiapprovalsystem.DTO.RegisterRequest;
import com.ecommers.multiapprovalsystem.Service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/login")
@Slf4j
public class AuthenticationController {

    @Autowired
    public AuthenticationService service;

    @PostMapping("/auth")
    public ResponseEntity<String> login(@RequestParam String email,
                                         @RequestParam String password){
        try {
            return new ResponseEntity<>(service.authentication(email, password), HttpStatus.OK);
        }catch (Exception e){
            log.error("Error while authenticating Error: {}",e.getMessage());
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@RequestBody RegisterRequest request){

    }
}
