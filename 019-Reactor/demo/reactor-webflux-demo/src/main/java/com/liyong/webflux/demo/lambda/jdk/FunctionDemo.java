package com.liyong.webflux.demo.lambda.jdk;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;

/**
 * 1、JDK自带的函数式接口
 * 
 * @author created by li.yong on 2020-4-1 21:48:44
 */
public class FunctionDemo {
	public static void main(String[] args) {
		//-------------- Predicate
		System.out.println("## Predicate ##");
		// 自定义断言-传统写法
		ITest<Integer> predicateOld = new ITest<Integer>() {
			@Override
			public boolean test(Integer data) {
				return data > 0;
			}
		};
		System.out.println("old: " + predicateOld.test(100));
		// 自定义断言-lambda写法
		ITest<Integer> predicateOldLambda = data -> data > 0;
		System.out.println("old lambda: " + predicateOldLambda.test(100));
		
		// JDK自带断言函数使用
		Predicate<Integer> predicate = data -> data > 0;
		System.out.println("lambda: " + predicate.test(10));
		
		
		
		//---------------- Consumer
		System.out.println("## Consumer ##");
		// 自定义消费者接口，实现消费者功能 - 传统写法
		IComsumer<String> consumerOld = new IComsumer<String>() {
			@Override
			public void accept(String str) {
				System.out.println(str);
			}
		};
		consumerOld.accept("consumer old");
		
		// 自定义消费者接口，实现消费者功能 - lambda写法
		IComsumer<String> consumerOldLambda = str -> System.out.println(str);
		consumerOldLambda.accept("consumer old lambda");
		
		// JDK自带消费者函数
		Consumer<String> consumer = str -> System.out.println(str);
		consumer.accept("lambda");
		
		
		//---------------- Function
		System.out.println("## Function ##");
		// 自定义Function接口，实现传一个入参，返回一个结果 - 传统写法
		IFunction<Integer, Integer> functionOld = new IFunction<Integer, Integer>() {
			@Override
			public Integer apply(Integer data) {
				return data * 2;
			}
		};
		System.out.println("old: " + functionOld.apply(100));
		
		// 自定义Function接口，实现传一个入参，返回一个结果 - lambda写法
		IFunction<Integer, Integer> functionOldLambda = data -> data * 2;
		System.out.println("old lambda: " + functionOldLambda.apply(100));
		
		// JDK自带Function函数
		Function<Integer, Integer> function = data -> data * 2;
		System.out.println("lambda: " + function.apply(100));
		
		
		
		//---------------- Supplier
		System.out.println("## Supplier ##");
		// 自定义提供者接口，实现返回数据 - 传统写法
		ISupplier<Double> supplierOld = new ISupplier<Double>() {
			@Override
			public Double get() {
				return 20.00;
			}
		};
		System.out.println("old: " + supplierOld.get());
		
		// 自定义提供者接口，实现返回数据 - lambda写法
		ISupplier<Double> supplierOldLambda = () -> 20.00;
		System.out.println("old lambda: " + supplierOldLambda.get());
		
		// JDK自带Supplier函数
		Supplier<Double> supplier = () -> 20.00;
		System.out.println("lambda: " + supplier.get());
		
		
		//---------------- UnaryOperator 一元函数
		System.out.println("## UnaryOperator ##");
		// 自定义一元函数-传统写法
		IUnaryOperator<String> unaryOperatorOld = new IUnaryOperator<String>() {
			@Override
			public String apply(String str) {
				return str.substring(str.length() / 2);
			}
		};
		System.out.println("old: " + unaryOperatorOld.apply("HelloHello"));
		
		// 自定义一元函数-lambda写法
		IUnaryOperator<String> unaryOperatorOldLambda = str -> str.substring(str.length() / 2);
		System.out.println("old lambda: " + unaryOperatorOldLambda.apply("HelloHello"));
		
		// JDK自带UnaryOperator函数
		UnaryOperator<String> unaryOperator = str -> str.substring(str.length() / 2);
		System.out.println("lambda: " + unaryOperator.apply("HelloHello"));
		
		//---------------- BiFunction 两个入参，一个返回值
		System.out.println("## BiFunction ##");
		BiFunction<Integer, Double, String> biFunction = (data1, data2) -> data1 + " -> " + data2;
		System.out.println("lambda: " + biFunction.apply(10, 50.32));
		
		//---------------- BinaryOperator 二院函数
		System.out.println("## BinaryOperator ##");
		BinaryOperator<Integer> binaryOperator = (data1, data2) -> data1 + data2;
		System.out.println("lambda: " + binaryOperator.apply(12, 15));
		
		/**
		 * 函数接口的优势： 
		 * 1、不需要定义更多的接口
		 * 2、函数接口支持级联操作
		 */
		BiFunction<Integer, Integer, String> binaryOperatorFunction = binaryOperator.andThen(total -> total + " 元");
		System.out.println(binaryOperatorFunction.apply(12, 15));
	}
}
