package com.mendozanews.apinews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@SpringBootApplication
public class ApiNewsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiNewsApplication.class, args);
        System.out.println("iniciado");
    }
}
