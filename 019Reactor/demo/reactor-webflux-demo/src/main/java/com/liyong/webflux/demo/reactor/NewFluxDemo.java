package com.liyong.webflux.demo.reactor;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * 2、Flux的创建
 * @author Administrator
 *
 */
public class NewFluxDemo {
	public static void main(String[] args) throws Exception {
		
		Flux.create(sink -> {
			for (int i = 0; i < 10; i++) {
				sink.next(Thread.currentThread().getName() + "  ->  " + i);
			}
			sink.complete();
		})
		.subscribeOn(Schedulers.elastic())
		.subscribe(System.out::println);
		
		
		System.in.read();
	}
	
	private void backUp() {

		// 可以指定序列中包含的全部元素。创建出来的 Flux 序列在发布这些元素之后会自动结束。
		Flux.just("Hello", "World").subscribe(System.out::println);
		
		// fromArray()，fromIterable()和 fromStream()：可以从一个数组、Iterable 对象或 Stream 对象中创建 Flux 对象。
		Flux.fromArray(new Integer[] {1, 2, 3}).subscribe(System.out::println);
		
		List<String> list = new ArrayList<>();
		list.add("Y20");list.add("Y30");list.add("Y40");
		Flux.fromIterable(list).subscribe(System.out::println);
		
		// 创建一个不包含任何元素，只发布结束消息的序列。
		// 在响应式编程中，流的传递是基于元素的，empty表示没有任何元素，所以不会进行后续传递，需要用switchIfEmpty等处理
		Flux.empty().subscribe(System.out::println);
		
		// 创建一个不包含任何消息通知的序列
		Flux.range(1, 10)
	    	.timeout(Flux.never(), v -> Flux.never())
	    	.subscribe(System.out::println);
		
		// 创建包含从 start 起始的 count 个数量的 Integer 对象的序列。
		Flux.range(55, 10).subscribe(System.out::println);
		
		System.out.println("-----------Flux.interval-----------");
		
		Flux.interval(Duration.of(10, ChronoUnit.SECONDS)).subscribe(System.out::println);
		
		/*
		 *
		 * generate()方法通过同步和逐一的方式来产生 Flux 序列。
		 * 序列的产生是通过调用所提供的 SynchronousSink 对象的 next()，complete()和 error(Throwable)方法来完成的。
		 * 逐一生成的含义是在具体的生成逻辑中，next()方法只能最多被调用一次。
		 * 在有些情况下，序列的生成可能是有状态的，需要用到某些状态对象。
		 * 此时可以使用 generate()方法的另外一种形式 generate(Callable stateSupplier, BiFunction<S,SynchronousSink,S> generator)，
		 * 其中 stateSupplier 用来提供初始的状态对象。在进行序列生成时，状态对象会作为 generator 使用的第一个参数传入，
		 * 可以在对应的逻辑中对该状态对象进行修改以供下一次生成时使用。
		 * 【Flux.generate single】通过 next()方法产生一个简单的值，然后通过 complete()方法来结束该序列。
		 * 如果不调用 complete()方法，所产生的是一个无限序列。
		 * 【Flux.generate murti】第二个序列的生成逻辑中的状态对象是一个 ArrayList 对象。
		 * 实际产生的值是一个随机数。产生的随机数被添加到 ArrayList 中。当产生了 10 个数时，通过 complete()方法来结束序列
		 */
		System.out.println("-----------Flux.generate single-----------");
		AtomicInteger index = new AtomicInteger(0);
		Flux.generate(sink -> {
			sink.next("H1");
			if(index.getAndIncrement() == 10) {
				sink.complete();
			}
		}).subscribe(System.out::println);
		
		System.out.println("-----------Flux.generate murti-----------");
		AtomicInteger index2 = new AtomicInteger(0);
		Flux.generate(ArrayList::new, (datas, sink) -> {
			int value = index2.getAndIncrement();
			datas.add(value);
		    sink.next(value);
		    if (datas.size() == 10) {
		        sink.complete();
		    }
		    return datas;
		}).subscribe(System.out::println);
		
		// create()方法与 generate()方法的不同之处在于所使用的是 FluxSink 对象。
		// FluxSink 支持同步和异步的消息产生，并且可以在一次调用中产生多个元素。
		// 重点说明，subscribe提供的consumer必须保证幂等性，否则失败重试，会导致重复消费。
		System.out.println("-----------Flux.create-----------");
		Flux.create(sink -> {
			for (int i = 0; i < 10; i++) {
		        sink.next(i);
		        try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		    }
			sink.complete();
		}).subscribe(System.out::println);
	}
}
