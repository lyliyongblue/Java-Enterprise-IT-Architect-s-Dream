package com.liyong.flux.service;

import java.util.Random;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class RemoteHelloService {
	Random random = new Random();
	public Mono<String> getData(String data, int delay) {
		long model = (System.currentTimeMillis() + random.nextInt(5000))%2;
		WebClient client = null;
		if(model == 0) {
			client = WebClient.create("http://192.168.50.220:8282/test/" + data + "/" + delay);
		} else {
			client = WebClient.create("http://192.168.50.220:8181/test/" + data + "/" + delay);
		}
		return client.get()
				.accept(MediaType.TEXT_PLAIN)
				.exchange()
				.flatMap(response -> response.bodyToMono(String.class));
	}
}
