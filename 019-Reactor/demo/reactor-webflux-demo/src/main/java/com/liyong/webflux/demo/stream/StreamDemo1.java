package com.liyong.webflux.demo.stream;

import java.util.stream.IntStream;

/**
 * 
 * @author Administrator
 *
 */
public class StreamDemo1 {
	public static void main(String[] args) {
		int[] datas = {1, 3, 9};
		
		/**
		 * 流中的中间操作：返回值如果还是流，那么就是中间操作
		 * 流中的终止操作：返回值不在是流，就是终止操作
		 */
		int sum = IntStream.of(datas).map(StreamDemo1::doubleNum).sum();
		System.out.println("sum: " + sum);
		
		// 惰性求值，如果流未调用终止操作，那么整个流上的逻辑是不执行的
		IntStream.of(datas).map(StreamDemo1::doubleNum);
	}
	
	public static int doubleNum(int i) {
		System.out.println(" * 2");
		return i*2;
	}
}
