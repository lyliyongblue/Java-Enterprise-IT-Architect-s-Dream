package com.liyong.webflux.demo.lambda.demo;

@FunctionalInterface
interface Couter {
	int incr(int init, int delay);
}

/**
 * 4、函数表达式
 * @author Administrator
 *
 */
public class LambdaDemo {
	public static void main(String[] args) {
		Couter couter = (init, delay) -> init + delay;
		System.out.println(couter.incr(10, 2));
	}
}
