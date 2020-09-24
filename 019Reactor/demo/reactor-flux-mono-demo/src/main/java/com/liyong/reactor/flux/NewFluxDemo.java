package com.liyong.reactor.flux;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import reactor.core.publisher.Flux;

public class NewFluxDemo {
	public static void main(String[] args) {
		// 可以指定序列中包含的全部元素。创建出来的 Flux 序列在发布这些元素之后会自动结束。
		Flux.just("Hello", "World").subscribe(System.out::println);
		
		// fromArray()，fromIterable()和 fromStream()：可以从一个数组、Iterable 对象或 Stream 对象中创建 Flux 对象。
		Flux.fromArray(new Integer[] {1, 2, 3}).subscribe(System.out::println);
		
		List<String> list = new ArrayList<>();
		list.add("Y20");list.add("Y30");list.add("Y40");
		Flux.fromIterable(list).subscribe(System.out::println);
		
		// 创建一个不包含任何元素，只发布结束消息的序列。
		Flux.empty().subscribe(System.out::println);
		
		// 创建包含从 start 起始的 count 个数量的 Integer 对象的序列。
		Flux.range(55, 10).subscribe(System.out::println);
		
		System.out.println("-----------Flux.interval-----------");
		
		Flux.interval(Duration.of(10, ChronoUnit.SECONDS)).subscribe(System.out::println);
		
		System.out.println("-----------Flux.generate single-----------");
		Flux.generate(sink -> {
			sink.next("H1");
			sink.complete();
		}).subscribe(System.out::println);
		
		System.out.println("-----------Flux.generate murti-----------");
		AtomicInteger index = new AtomicInteger(0);
		Flux.generate(ArrayList::new, (datas, sink) -> {
			int value = index.getAndIncrement();
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
