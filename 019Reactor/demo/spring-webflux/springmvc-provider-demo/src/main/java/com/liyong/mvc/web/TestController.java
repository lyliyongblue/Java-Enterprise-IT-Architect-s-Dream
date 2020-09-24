package com.liyong.mvc.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.liyong.mvc.service.HelloService;

@RestController
public class TestController {
	
	private final HelloService helloService;
	
	public TestController(HelloService helloService) {
		this.helloService = helloService;
	}
	
	@GetMapping("/test/{data}/{delay}")
	public String test(@PathVariable("data") String data, @PathVariable("delay") int delay) {
		return helloService.getData(data, delay);
	}
}
