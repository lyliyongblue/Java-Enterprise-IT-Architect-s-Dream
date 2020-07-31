package com.liyong.webflux.demo.reactor;

import java.time.Duration;

import reactor.core.publisher.Flux;

public class HotDataDemo {
	public static void main(String[] args) throws Exception {
		Flux<Long> source = Flux.interval(Duration.ofMillis(1000))
				.take(10)
				.publish()
				.autoConnect();
		
		source.subscribe();
		
		Thread.sleep(5000);
		
		source.subscribe(System.out::println);
		
		System.in.read();
	}
}
