package com.liyong.flux.web;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liyong.flux.entity.User;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

@RestController
@RequestMapping("/sse")
public class SseController {
	
	@GetMapping
	public Flux<ServerSentEvent<User>> getUsers() {
		return Flux.interval(Duration.ofSeconds(1))
				.map(seq -> Tuples.of(seq, ThreadLocalRandom.current().nextInt()))
				.map(data -> {
					User user = new User();
					user.setName("name-" + data.getT1());
					user.setEmail("email->" + data.getT2());
					System.out.println(LocalDateTime.now() + " --> " + user);
					return ServerSentEvent
							.<User>builder()
							.event("sss")
							.id(user.getId())
							.data(user)
							.build();
				});
	}
}
