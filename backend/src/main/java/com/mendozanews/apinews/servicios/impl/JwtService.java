package com.mendozanews.apinews.servicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mendozanews.apinews.tokens.JwtUtil;

@Service
public class JwtService {

    @Autowired
    private JwtUtil jwtUtil;

    public String generateToken(String username) {
        return jwtUtil.generateToken(username);
    }

    public Boolean validateToken(String token, String username) {
        return jwtUtil.validateToken(token,  username);
    }
}
