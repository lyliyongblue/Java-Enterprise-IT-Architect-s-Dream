package com.liyong.mvc.service;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

@Service
public class HelloService {
	public String getData(String data, int delay) {
		System.out.println(Thread.currentThread().getName() + " data: " + data);
		try {
			TimeUnit.MILLISECONDS.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return data + "->" + delay;
	}
}
