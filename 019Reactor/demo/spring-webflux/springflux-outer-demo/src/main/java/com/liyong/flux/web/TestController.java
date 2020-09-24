package com.liyong.flux.web;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.liyong.flux.service.HelloService;

import reactor.core.publisher.Mono;

@RestController
public class TestController {
	
	private final HelloService helloService;
	
	public TestController(HelloService helloService) {
		this.helloService = helloService;
	}
	
	@GetMapping("/test/{delay}")
	public Mono<String> test(@PathVariable("delay") int delay) {
		String uuid = UUID.randomUUID().toString();
		System.out.println("Contro*: " + Thread.currentThread().getName() + "  uuid: " + uuid);
		return helloService.getData(uuid, delay);
	}
}
