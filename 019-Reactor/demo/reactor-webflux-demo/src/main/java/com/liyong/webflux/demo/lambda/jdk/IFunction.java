package com.liyong.webflux.demo.lambda.jdk;

public interface IFunction<T, R> {
	R apply(T data);
}
