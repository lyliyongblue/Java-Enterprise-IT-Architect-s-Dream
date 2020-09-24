package com.liyong.webflux.demo.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxMessageDemo {
	public static void main(String[] args) throws Exception {
		
		
		
	}

	public void backUp() {
		
		// 通过 subscribe()方法处理正常和错误消息。
		// 如果数据量中存在异常信息，那么流处理将直接终止，这个时候Complete Consumer也无法执行
		/*
		 * 输出结果：
		 * 1
		 * 2
		 * 处理完成
		 */
		Flux.just(1, 2)
			.subscribe(
					System.out::println,
					System.err::println,
					() -> {
						System.out.println("处理完成");
					});
		
		// 提供错误数据的处理
		/*
		 * 输出结果：
		 * 2
		 * 3
		 * java.lang.IllegalStateException
		 */
		Flux.just(2, 3)
			.concatWith(Mono.error(new IllegalStateException()))
			.subscribe(
					System.out::println,
					System.err::println,
					() -> {
						System.out.println("处理完成");
					});
		
		
		
		
		
		// 正常的消息处理相对简单。当出现错误时，有多种不同的处理策略。
		// 通过 onErrorReturn()方法返回一个默认值。
		Flux.just(1, 2)
        	.concatWith(Mono.error(new IllegalStateException()))
        	.onErrorReturn(0)
        	.subscribe(System.out::println);
		
		// 当出现错误时，使用doOnError，可以处理异常的数据
		// 当出现异常时，整个流的处理终止，后面的数据将不再继续处理
		Flux.just(1, 2)
	    	.concatWith(Mono.error(new IllegalStateException("非法状态")))
	    	.concatWith(Mono.error(new IllegalStateException("非法状态2")))
	    	.doOnError(error -> {
	    		System.out.println(error.getMessage());
	    	})
	    	.doOnError(IllegalStateException.class, error -> {
	    		System.out.println("IllegalState-Exception: " + error.getMessage());
	    	})
	    	.onErrorReturn(0)
	    	.subscribe(System.out::println, System.out::println, () -> System.out.println("it's over"));
		
		
		// 出现错误时，使用onErrorResume接收所有异常，然后根据异常类型来选择返回不同的结果流
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
		// 输出结果：12120
		Flux.just(1, 2)
        	.concatWith(Mono.error(new IllegalStateException()))
        	.retry(1)
        	// onErrorReturn放在retry只后，就会先retry完成，还有异常，会执行onErrorReturn的逻辑
        	.onErrorReturn(0)
        	.subscribe(System.out::println);
		

	}
}
