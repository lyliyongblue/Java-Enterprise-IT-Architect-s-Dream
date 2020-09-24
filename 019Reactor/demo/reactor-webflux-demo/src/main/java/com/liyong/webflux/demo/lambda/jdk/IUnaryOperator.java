package com.liyong.webflux.demo.lambda.jdk;

public interface IUnaryOperator<T> {
	T apply(T data);
}
