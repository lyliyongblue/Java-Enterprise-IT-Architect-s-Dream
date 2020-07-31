package com.liyong.reactor.flux;

import java.util.Optional;

import reactor.core.publisher.Mono;

public class NewMonoDemo {
	public static void main(String[] args) {
		Mono.just("Hello").subscribe(System.out::println);
		Mono.empty().subscribe(System.out::println);
		Mono.never().subscribe(System.out::println);
		Mono.fromSupplier(() -> "Hello Supplier").subscribe(System.out::println);
		Mono.justOrEmpty(Optional.of("Hello Optional")).subscribe(System.out::println);
		Mono.create(sink -> {
			sink.success("Hello create sink");
		}).subscribe(System.out::println);
	}
}
