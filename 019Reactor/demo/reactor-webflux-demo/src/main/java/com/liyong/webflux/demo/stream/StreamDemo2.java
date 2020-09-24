package com.liyong.webflux.demo.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
/**
 * 流创建2
 * @author Administrator
 *
 */
public class StreamDemo2 {
	public static void main(String[] args) {
		// 从集合创建
		List<String> list = new ArrayList<>();
		list.stream();
		list.parallelStream();

		// 从数组创建
		Arrays.stream(new int[] { 1, 3, 4 });

		// 创建数字流
		IntStream.of(1, 2, 3);
		IntStream.range(1, 3).forEach(System.out::println);
		IntStream.rangeClosed(1, 3).forEach(System.out::println);
		// 通过of方法创建字符串流
		Stream.of("111", "222", "333").forEach(System.out::println);
		
		IntPredicate filter = i -> i > 0 && i < 100;
		
		// 使用random创建一个无限流
		new Random().ints().filter(filter).limit(3).forEach(System.out::println);
		
		// 自己产生流
		IntStream.generate(new Random()::nextInt).filter(filter).limit(20).forEach(System.out::println);
	}
}
