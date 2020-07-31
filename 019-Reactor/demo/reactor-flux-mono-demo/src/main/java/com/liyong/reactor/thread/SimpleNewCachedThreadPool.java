package com.liyong.reactor.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleNewCachedThreadPool {
	
	static class Worker implements Runnable {
		@Override
		public void run() {
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " exe success");
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 0; i < 10; i++) {
			Thread.sleep(10);
			executor.submit(new Worker());
		}
		executor.shutdown();
	}
}
