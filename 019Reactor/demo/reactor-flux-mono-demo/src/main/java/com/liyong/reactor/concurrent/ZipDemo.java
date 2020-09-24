package com.liyong.reactor.concurrent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ZipDemo {
	public static void main(String[] args) {
		String param = "Hello";
		Mono<String> step1 = step1(param);
		Mono<String> step2 = step2(param);
		
		Flux<String> result = Flux.zip(items -> {
			System.out.println("zip: " + Thread.currentThread().getName());
			return Flux.just(items);
		}, step1, step2).publishOn(Schedulers.elastic()).map(data -> {
			System.out.println("map: " + Thread.currentThread().getName());
			return data.toString();
		});
		
		result.subscribe(System.out::println);
	}
	
	private static Mono<String> step1(String param) {
		return Mono.just(param).publishOn(Schedulers.elastic()).map(request -> {
			System.out.println("step1: " + Thread.currentThread().getName());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "step1: " + param;
		});
	}
	
	private static Mono<String> step2(String param) {
		return Mono.just(param).map(request -> {
			System.out.println("step2: " + Thread.currentThread().getName());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "step2: " + param;
		});
	}
}
