package com.liyong.asyn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liyong.asyn.util.LongRunningProcess;

@RestController
public class HelloController {
	@GetMapping("/hello3")
	public String hello3() {
		new LongRunningProcess().run();
		return "Hello world";
	}
}
