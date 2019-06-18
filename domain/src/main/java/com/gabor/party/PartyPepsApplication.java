package com.gabor.party;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAutoConfiguration
@EnableAspectJAutoProxy
public class PartyPepsApplication {


    public static void main(String[] args) {
        SpringApplication.run(PartyPepsApplication.class, args);
    }
}

