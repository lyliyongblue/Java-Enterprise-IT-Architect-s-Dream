package com.liyong.reactor.flux;

import java.time.Duration;

import reactor.core.publisher.Flux;

public class FluxOptionsDemo {
	public static void main(String[] args) {
		Flux.just(6, 10).flatMap(x -> Flux.just(x, x * 2))
			.toStream()
			.forEach(System.out::println);
		
		Flux.just(6, 10).flatMapSequential(x -> Flux.just(x, x * 2))
			.toStream()
			.forEach(System.out::println);
	}
	
	public void backUp() {
		System.out.println("-----------Flux range reduce-----------");
		Flux.range(1, 100).reduce((x, y) -> x + y).subscribe(System.out::println);
		
		System.out.println("-----------Flux range take-----------");
		Flux.range(1, 1000).take(10).subscribe(System.out::println);
		System.out.println("-----------Flux range takeLast-----------");
		Flux.range(1, 1000).takeLast(10).subscribe(System.out::println);
		System.out.println("-----------Flux range takeWhile-----------");
		Flux.range(1, 1000).takeWhile(i -> i < 10).subscribe(System.out::println);
		System.out.println("-----------Flux range takeUntil-----------");
		Flux.range(1, 1000).takeUntil(i -> i == 10).subscribe(System.out::println);
		
		System.out.println("-----------Flux zipWith combine-----------");
		Flux.just("a", "b").zipWith(Flux.just("c", "d", "e"), (data1, data2) -> {
			return data1 + " -> " + data2;
		}).subscribe(System.out::println);
		
		System.out.println("-----------Flux zipWith-----------");
		Flux.just("a", "b").zipWith(Flux.just("c", "d")).subscribe(System.out::println);
		
		System.out.println("-----------Flux.range window-----------");
		Flux.range(1, 100).window(5).subscribe(flux -> flux.subscribe(System.out::println));
		
		System.out.println("-----------Flux.range filter-----------");
		Flux.range(1, 10).filter(i -> i % 2 == 0).subscribe(System.out::println);
		
		System.out.println("-----------Flux.range bufferWhile-----------");
		Flux.range(1, 10).bufferWhile(data -> data%4 == 0).subscribe(System.out::println);
		
		System.out.println("-----------Flux.range bufferUntil-----------");
		Flux.range(1, 10).bufferUntil(data -> data%4 == 0).subscribe(System.out::println);
		
		System.out.println("-----------Flux.create bufferTimeout-----------");
		Flux.create(sink -> {
			for (int i = 0; i < 20; i++) {
		        sink.next(i);
		        try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		    }
			sink.complete();
		}).bufferTimeout(5, Duration.ofSeconds(2)).subscribe(System.out::println);
		
		System.out.println("-----------Flux.create buffer-----------");
		Flux.create(sink -> {
			for (int i = 0; i < 20; i++) {
		        sink.next(i);
		        try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		    }
			sink.complete();
		}).buffer(5).subscribe(System.out::println);
		
		Flux.range(1, 100).buffer(10).subscribe(System.out::println);
	}
}
