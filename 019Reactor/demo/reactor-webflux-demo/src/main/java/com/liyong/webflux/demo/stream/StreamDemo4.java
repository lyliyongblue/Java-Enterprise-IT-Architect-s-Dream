package com.liyong.webflux.demo.stream;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 4、流的收集
 * @author Administrator
 *
 */
public class StreamDemo4 {
	public static void main(String[] args) {
		String str = "public static void main String args";
		
		// 并行流的使用，通过打印结果，可以看到是多线程遍历的
		Stream.of(str.split(" "))
			// 并行流
			.parallel().forEach(s -> {
			System.out.println(Thread.currentThread().getName() + " : " + s);
		});
		
		System.out.println("## parallel forEachOrdered ##");
		// 并行流+顺序执行
		Stream.of(str.split(" ")).parallel().forEachOrdered(s -> {
			System.out.println(Thread.currentThread().getName() + " : " + s);
		});
		

		// 流的收集
		System.out.println("## Stream Collectors ##");
		List<String> list = Stream.of(str.split(" ")).collect(Collectors.toList());
		System.out.println(list);
		
		// 使用 reduce 拼接字符串
		String result = Stream.of(str.split(" ")).reduce("", (s1, s2) -> s1 + " | " + s2);
		System.out.println("result: " + result);
		
		// max 的使用
		Optional<String> max = Stream.of(str.split(" ")).max((s1, s2) -> s1.length() - s2.length());
		System.out.println("max: " + max.get());
		
		// 短路操作
		System.out.println("findAny: " + new Random().ints().findAny().getAsInt());
		System.out.println("findFirst: " + new Random().ints().findFirst().getAsInt());
	}
}
