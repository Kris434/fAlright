package com.falright.falright;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class FAlrightApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(FAlrightApplication.class, args);
    }

}
