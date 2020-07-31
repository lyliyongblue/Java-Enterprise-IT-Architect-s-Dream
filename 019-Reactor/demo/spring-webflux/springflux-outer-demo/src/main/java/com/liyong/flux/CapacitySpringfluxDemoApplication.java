package com.liyong.flux;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.liyong.flux.handler.TestHandler;

@SpringBootApplication
public class CapacitySpringfluxDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapacitySpringfluxDemoApplication.class, args);
	}

	@Autowired
	private TestHandler testHandler;

	@Bean
	public RouterFunction<ServerResponse> routes() {
		return RouterFunctions.route(GET("/func/test/{delay}"), testHandler::test);
	}

}
