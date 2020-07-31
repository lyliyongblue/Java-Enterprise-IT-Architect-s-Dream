package com.liyong.webflux.demo.lambda.jdk;

public interface IComsumer<T> {
	void accept(T str);
}
