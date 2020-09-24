package com.liyong.asyn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

//@EnableAsync
@ServletComponentScan
@SpringBootApplication
public class AsynServletApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsynServletApplication.class, args);
	}

}
