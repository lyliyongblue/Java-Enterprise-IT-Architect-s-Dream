package com.liyong.reactor.thread;

import java.io.IOException;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SimpleNewScheduledThreadPool {
	static class Worker implements Runnable {

		private final int index;

		public Worker(int index) {
			this.index = index;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(LocalTime.now() + " index:" + index + "  " + Thread.currentThread().getName() + " exe success");
		}
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
		
		System.out.println(LocalTime.now() + " thread index: " + 1111 + " submit success");
		executor.schedule(new Worker(1111), 5, TimeUnit.SECONDS);
		
		System.out.println(LocalTime.now() + " thread index: " + 2222 + " submit success");
		executor.scheduleWithFixedDelay(new Worker(2222), 6000, 200, TimeUnit.MILLISECONDS);
		
		System.out.println(LocalTime.now() + " thread index: " + 3333 + " submit success");
		executor.scheduleAtFixedRate(new Worker(3333), 6000, 200, TimeUnit.MILLISECONDS);
		
		System.in.read();
		executor.shutdown();
	}
}
