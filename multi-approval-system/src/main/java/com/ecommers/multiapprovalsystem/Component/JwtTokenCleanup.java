package com.ecommers.multiapprovalsystem.Component;


import com.ecommers.multiapprovalsystem.Model.Token;
import com.ecommers.multiapprovalsystem.Repository.TokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JwtTokenCleanup {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private TokenRepo tokenRepo;

    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void cleanUp(){
        List<Token> tokens = tokenRepo.findAll();
        tokens.stream().forEach(token -> {
            if(jwtUtil.isTokenExpired(token.getLoginId())) tokenRepo.deleteByLoginId(token.getLoginId());
        });
    }
}
