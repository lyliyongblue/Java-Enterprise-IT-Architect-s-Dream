package com.liyong.webflux.demo.lambda.curry;

import java.util.function.Function;

/**
 * 3、级联表达式和柯里化
 * 柯里化： 把多个参数的函数转换为只有一个参数的函数
 * 柯里化的目的：函数标准化
 * 高阶函数：返回函数的函数
 * 
 * 
 * 函数式编程: do what  命令式编程：how do
 * @author created by li.yong on 2020-4-1 23:42:02
 */
public class CurryDemo {
	public static void main(String[] args) {
		// Function<x, Function<y, x + y>> 实现x+y的级联表达式
		Function<Integer, Function<Integer, Integer>> function = x -> y -> x + y;
		System.out.println(function.apply(10).apply(22));
		
		// Function<x, Function<y, Function<z, x + y + z>> 实现x+y+y的级联表达式
		Function<Integer, Function<Integer, Function<Integer, Integer>>> function3 = x -> y -> z -> x + y + z;
		System.out.println(function3.apply(1).apply(2).apply(3));
	}
}
