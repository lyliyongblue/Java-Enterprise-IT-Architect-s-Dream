package com.liyong.webflux.demo.stream;

import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 4、流的并行、串行执行 并行：parallel 串行：sequential
 * 多次调用 parallel / sequential, 以最后一次调用为准.
 * 
 * @author Administrator
 *
 */
public class StreamDemo5 {

	public static void main(String[] args) throws IOException {
		// 多次调用 parallel / sequential, 以最后一次调用为准.
		// parallelAndSequential();
		
		// 并行流使用公共的 ForkJoinPool.commonPool线程池进行执行
		// 默认线程数是当前机器的CPU个数
		// 使用这个属性可以修改默认的线程数
		// System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "20");
		// parallelCommonPool();
		
		// 使用自己的线程池, 不使用默认线程池, 防止任务被阻塞
		parallelNewPool();
		
		System.in.read();
	}

	private static void parallelNewPool() {
		ForkJoinPool pool = new ForkJoinPool(10);
		pool.submit(() -> {
			IntStream.range(1, 100).parallel().peek(StreamDemo5::debug).count();
		});
	}

	private static void parallelCommonPool() {
		IntStream.rangeClosed(1, 4)
				// 调用parallel产生并行流
				.parallel().peek(StreamDemo5::debug).count();
	}

	private static void parallelAndSequential() {
		// 多次调用 parallel / sequential, 以最后一次调用为准.
		IntStream.range(1, 1)
				// 调用parallel产生并行流
				.parallel().peek(StreamDemo5::debug)
				// 调用sequential 产生串行流
				.sequential().peek(StreamDemo5::debug2).count();
	}

	public static void debug(int i) {
		System.out.println(Thread.currentThread().getName() + " debug " + i);
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void debug2(int i) {
		System.err.println(Thread.currentThread().getName() + " debug2 " + i);
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
