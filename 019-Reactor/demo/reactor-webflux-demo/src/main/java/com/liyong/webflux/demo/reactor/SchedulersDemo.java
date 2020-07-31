package com.liyong.webflux.demo.reactor;

import java.io.IOException;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class SchedulersDemo {
	public static void main(String[] args) throws IOException {
		/*
		 * 调度器：
		 * Schedulers.immediate() 当前线程
		 * Schedulers.single() 单一的可复用的线程
		 * Schedulers.elastic() 使用弹性的线程池，线程池中的线程是可以复用的。当所需要时，新的线程会被创建。如果一个线程闲置太长时间，则会被销毁。该调度器适用于 I/O 操作相关的流的处理。
		 * Schedulers.parallel() 使用对并行操作优化的线程池，其中的线程数量取决于 CPU 的核的数量。该调度器适用于计算密集型的流的处理。
		 * Schedulers.timer() 使用支持任务调度的调度器
		 * Schedulers.fromExecutorService() 自定义线程池
		 */
		
		/*
		 * 某些操作符默认就已经使用了特定类型的调度器。比如 intervalMillis()方法创建的流就使用了由 Schedulers.timer()创建的调度器。
		 * 通过 publishOn()和 subscribeOn()方法可以切换执行操作的调度器。
		 * 其中 publishOn()方法切换的是操作符的执行方式；
		 * 而 subscribeOn()方法切换的是产生流中元素时的执行方式。
		 */
		
		/*
		 * subscribeOn的使用位置不重要，如果重复使用，只会以第一次的规则为准
		 * publishOn使用后，下面的操作符执行方式以当前操作符向上最近的publishOn规则为准
		 * 
		 */
		Flux.create(sink -> {
			sink.next(Thread.currentThread().getName());
			sink.complete();
		})
		.subscribeOn(Schedulers.parallel())
		.publishOn(Schedulers.single())
		.map(str -> String.format("[%s] %s", Thread.currentThread().getName(), str))
		.publishOn(Schedulers.elastic())
		.map(str -> String.format("[%s] %s", Thread.currentThread().getName(), str))
		.publishOn(Schedulers.parallel())
		.subscribe(str -> {
			System.out.println(Thread.currentThread().getName() + " print -> " + str);
		});
		
		System.in.read();
	}
}
