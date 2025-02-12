package com.fpeng2288.authguard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AuthGuardApplication {

    public static void main(String[] args) {

        SpringApplication.run(AuthGuardApplication.class, args);
    }

}
