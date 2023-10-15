package com.example.ifelsejsonparser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class IfElseJsonParser {
    public static void main(String[] args) {
            log.info("APP STARTED");
            SpringApplication.run(IfElseJsonParser.class, args);
    }
}