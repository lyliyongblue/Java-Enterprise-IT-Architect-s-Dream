package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class DemoApplicationTests {

	private final RestTemplate restTemplate = new RestTemplate();

	@Test
	void testHello() {
		String url = "http://localhost:8081/hello?name={name}";
		ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class, "李勇");
		System.out.println("result: " + entity.getBody());
	}

}
