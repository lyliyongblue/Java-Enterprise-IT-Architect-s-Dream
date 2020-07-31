package com.liyong.asyn.util;

import java.util.concurrent.ThreadLocalRandom;

public class LongRunningProcess {
	public void run() {
		try {
//			int millis = ThreadLocalRandom.current().nextInt(2000);
			int millis = 200;
			String currentThread = Thread.currentThread().getName();
			System.out.println(currentThread + " sleep for " + millis + " milliseconds.");
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
