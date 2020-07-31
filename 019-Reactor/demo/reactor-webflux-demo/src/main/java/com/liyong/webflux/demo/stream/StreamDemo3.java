package com.liyong.webflux.demo.stream;

import java.util.Random;
import java.util.stream.Stream;

/***
 * 3、对流的中台操作进行演示
 * @author Administrator
 *
 */
public class StreamDemo3 {
	public static void main(String[] args) {
		/**
		 * 中间操作（无状态）： map flatMap filter peek
		 * 中间操作（有状态）：distinct sorted limit/skip
		 */
		
		String str = "public static void main String args";
		
		// 需求： 将一句话中大于4个字母的单词，转换成小写，输出打印  map将流中的每个元素进行转换
		// 将数组转换成流
		Stream.of(str.split(" "))
			// 过滤成长度大于4的单词
			.filter(s -> s.length() > 4)
			// 将字符串全部小写
			.map(s -> s.toLowerCase())
			// 遍历打印结果
			.forEach(System.out::println);
		
		// 需求： 将一句话中大于4个字母的单词，拆分成字节，并打印输出统计结果
		// flatMap 将一个元素中的指定的多元素属性拉平
		Stream.of(str.split(" "))
			.filter(s -> s.length() > 4)
			.flatMap(s -> s.chars().boxed())
			.forEach(c -> System.out.println((char)c.intValue()));
		
		// peek 主要用于调试，它与forEach的区别，在于 peek是中间操作，而forEach是终止操作。
		System.out.println("## peek ##");
		Stream.of(str.split(" ")).peek(System.out::println).forEach(System.out::println);
		
		// limit 的使用，用于有N个元素的流，进行截断
		new Random().ints().filter(i -> i > 0 && i < 100).limit(10).forEach(System.out::println);
		
	}
}
