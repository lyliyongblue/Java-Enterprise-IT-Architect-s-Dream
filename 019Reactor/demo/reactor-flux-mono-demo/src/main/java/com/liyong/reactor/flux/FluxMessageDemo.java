package com.liyong.reactor.flux;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class FluxMessageDemo {
	public static void main(String[] args) {
		
		AtomicInteger index = new AtomicInteger(0);
		Flux.generate(ArrayList::new, (list, sink) -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String data = "[" + Thread.currentThread().getName() + "]";
			sink.next(data);
			list.add(data);
			if(index.getAndIncrement() == 5) {
				sink.complete();
			}
			return list;
		})
		.publishOn(Schedulers.single())
		.map(x -> String.format("[%s] %s", Thread.currentThread().getName(), x))
		.publishOn(Schedulers.elastic())
		.map(x -> String.format("[%s] %s", Thread.currentThread().getName(), x))
		.subscribeOn(Schedulers.parallel())
		.toStream().forEach(System.out::println);;
	}

	public void backUp() {
		// 提供错误数据的处理
		Flux.just(1, 2).mergeWith(Mono.error(new IllegalStateException())).subscribe(System.out::println,
				System.err::println);

		Flux.just(2, 3).concatWith(Mono.error(new IllegalStateException())).subscribe(System.out::println,
				System.err::println);
		
		// 异常数据处理， onErrorReturn返回固定数值
		Flux.just(1, 2)
        	.concatWith(Mono.error(new IllegalStateException()))
        	.onErrorReturn(0)
        	.subscribe(System.out::println);
		
		// 出现错误时根据异常类型来选择流
		Flux.just(1, 2)
			// .concatWith(Mono.error(new IllegalStateException()))
			.concatWith(Mono.error(new IllegalArgumentException()))
			.onErrorResume(e -> {
				if (e instanceof IllegalStateException) {
	                return Mono.just(0);
	            } else if (e instanceof IllegalArgumentException) {
	                return Mono.just(-1);
	            }
				return Mono.empty();
			})
			.subscribe(System.out::println);
		
		// 使用 retry 操作符进行重试
		Flux.just(1, 2)
        	.concatWith(Mono.error(new IllegalStateException()))
        	.retry(1)
        	// onErrorReturn放在retry只有，就会先retry完成，还有异常，会执行onErrorReturn的逻辑
        	.onErrorReturn(0)
        	.subscribe(System.out::println);
	}
}
