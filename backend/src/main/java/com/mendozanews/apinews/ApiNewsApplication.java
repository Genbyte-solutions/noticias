package com.mendozanews.apinews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ApiNewsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiNewsApplication.class, args);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "123456";
        String hashedPassword = passwordEncoder.encode(rawPassword);
        System.out.println(hashedPassword);
    }
}
