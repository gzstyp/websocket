package com.ricky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * Created by baiguantao on 2017/8/5.
 */
@SpringBootApplication
@Configuration
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}