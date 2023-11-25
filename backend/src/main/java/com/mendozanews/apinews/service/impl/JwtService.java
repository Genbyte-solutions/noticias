package com.mendozanews.apinews.service.impl;
import com.mendozanews.apinews.service.interfaces.IJwtService;
import org.springframework.stereotype.Service;
import com.mendozanews.apinews.tokens.JwtUtil;

@Service
public class JwtService implements IJwtService {
    private final JwtUtil jwtUtil;

    public JwtService (JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    public String generateToken(String username) {
        return jwtUtil.generateToken(username);
    }

    public Boolean validateToken(String token, String username) {
        return jwtUtil.validateToken(token,  username);
    }
}
