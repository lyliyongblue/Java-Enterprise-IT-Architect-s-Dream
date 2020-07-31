package com.liyong.webflux.demo.lambda.jdk;

public interface ITest<T> {
	boolean test(T data);
}
