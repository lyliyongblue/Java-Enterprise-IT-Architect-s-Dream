package com.yong.spring.jpa.jms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class SpringJtaApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringJtaApplication.class, args);
    }
}
