package com.petwellness;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PetwellnessApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetwellnessApiApplication.class, args);
    }

}
