package com.mendozanews.apinews.service.interfaces;

public interface IJwtService {

    public String generateToken(String username);

    public Boolean validateToken(String token, String username);
}
