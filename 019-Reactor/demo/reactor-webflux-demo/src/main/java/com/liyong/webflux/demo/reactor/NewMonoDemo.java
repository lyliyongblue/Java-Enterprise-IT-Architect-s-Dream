package com.liyong.webflux.demo.reactor;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import reactor.core.publisher.Mono;

/**
 * 1、Mono的创建
 * @author Administrator
 *
 */
public class NewMonoDemo {
	public static void main(String[] args) {
		// 创建对象
		Mono.just("Hello").subscribe(System.out::println);
		// 创建一个不包含任何元素，只发布结束消息的序列
		Mono.empty().subscribe(System.out::println);
		// empty里面至少还有一个结束消息，而never则是真的啥都没有
		Mono.never().subscribe(System.out::println);
		// 分别从  Callable、CompletionStage、CompletableFuture、Runnable 和 Supplier 中创建 Mono。
		Mono.fromCallable(() -> "9999").subscribe(System.out::println);
		Mono.fromCompletionStage(CompletableFuture.supplyAsync(() -> "fromCompletionStage异步返回的结果")).subscribe(System.out::println);
		Mono.fromFuture(CompletableFuture.supplyAsync(() -> "fromFuture异步返回的结果")).subscribe(System.out::println);
		Mono.fromRunnable(() -> {});
		Mono.fromSupplier(() -> "Hello Supplier").subscribe(System.out::println);
		
		// 创建一个 Mono 序列，在指定的延迟时间之后，产生数字 0 作为唯一值。
		Mono.delay(Duration.ofSeconds(3)).doOnNext(System.out::println).block();
		
		// 从一个 Optional 对象或可能为 null 的对象中创建 Mono。只有 Optional 对象中包含值或对象不为 null 时，Mono 序列才产生对应的元素。
		Mono.justOrEmpty(Optional.of("Hello Optional")).subscribe(System.out::println);
		// 还可以通过 create()方法来使用 MonoSink 来创建 Mono
		Mono.create(sink -> {
			sink.success("Hello create sink");
		}).subscribe(System.out::println);
		// defer(Supplier supplier) 可以依赖supplier延时的提供来创建一个新的Mono
		Mono.defer(() -> Mono.just("Good")).subscribe(System.out::println);
	}
}
