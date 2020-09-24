package com.liyong.reactor.thread;

import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleNewSingleThreadPool {
	static class Worker implements Runnable {

		private final int index;

		public Worker(int index) {
			this.index = index;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(LocalTime.now() + " index:" + index + "  " + Thread.currentThread().getName() + " exe success");
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		for (int i = 0; i < 5; i++) {
			Thread.sleep(10);
			System.out.println(LocalTime.now() + " thread index: " + (i + 1) + " submit success");
			executor.submit(new Worker(i + 1));
		}
		executor.shutdown();
	}
}
