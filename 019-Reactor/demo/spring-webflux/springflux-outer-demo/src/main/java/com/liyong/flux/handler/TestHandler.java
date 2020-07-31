package com.liyong.flux.handler;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.liyong.flux.service.HelloService;

import reactor.core.publisher.Mono;

@Component
public class TestHandler {
	private final HelloService helloService;
	public TestHandler(HelloService helloService) {
		this.helloService = helloService;
	}
	
	public Mono<ServerResponse> test(ServerRequest request) {
		int delay = Integer.valueOf(request.pathVariable("delay"));
		String uuid = UUID.randomUUID().toString();
		return ServerResponse.ok().body(helloService.getData(uuid, delay), String.class);
	}
}
