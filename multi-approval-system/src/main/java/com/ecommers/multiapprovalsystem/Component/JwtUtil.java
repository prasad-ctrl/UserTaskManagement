package com.ecommers.multiapprovalsystem.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ecommers.multiapprovalsystem.Repository.TokenRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtUtil {
    @Autowired
    private TokenRepo loginRepo;
    private static final String SECRET_KEY = "task_manager_1234$";
    private static final long EXPIRATION_TIME = 60 * 60 * 1000;

    public String generateToken(String username){
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public boolean verifyToken(String token){
        try{
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
            verifier.verify(token);
            return true;
        }catch (JWTVerificationException e){
            log.error("Verfication Failed ");
        }
        return false;
    }
    public String getUsername(String token){
        try{
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
            return decodedJWT.getSubject();
        }catch (JWTVerificationException e){
            log.error("Error token");
        }
        return null;
    }
    public boolean isTokenExpired(String token){
        try{
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
            return decodedJWT.getExpiresAt().before(new Date());
        }catch (JWTVerificationException e){
            log.error("Error token");
        }
        return false;
    }

    public void removeToken(String token){
        loginRepo.deleteByLoginId(token);
    }


}
