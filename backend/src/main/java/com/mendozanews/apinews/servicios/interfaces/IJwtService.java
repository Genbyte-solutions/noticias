package com.mendozanews.apinews.servicios.interfaces;

public interface IJwtService {

    public String generateToken(String username);

    public Boolean validateToken(String token, String username);
}
