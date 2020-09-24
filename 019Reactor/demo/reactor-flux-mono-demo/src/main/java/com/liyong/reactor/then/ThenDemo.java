package com.liyong.reactor.then;

import reactor.core.publisher.Mono;

public class ThenDemo {
	public static void main(String[] args) {
		Mono.just("1").then().and(Mono.just("2")).map(data -> data + " --> ").subscribe(System.out::println);
	}
}
