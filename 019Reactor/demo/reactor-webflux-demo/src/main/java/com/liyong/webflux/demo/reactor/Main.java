package com.liyong.webflux.demo.reactor;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import reactor.core.publisher.Flux;

public class Main {
	public static void main(String[] args) {
		Flux.interval(Duration.of(10, ChronoUnit.SECONDS)).subscribe(System.out::println);
	}
}
