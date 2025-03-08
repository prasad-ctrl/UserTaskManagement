package com.ecommers.multiapprovalsystem.Component;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "task_manager_1234$";
    private static final long EXPIRATION_TIME = 60 * 60 * 1000;



}
