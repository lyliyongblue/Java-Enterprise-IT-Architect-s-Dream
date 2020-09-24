package com.liyong.gc;

import java.util.LinkedList;
import java.util.List;

/**
 * 生成大量的放到老年代，并且移除引用，使其在老年代中形成大量的垃圾对象<br>
 * 一个线程一定时间间隔打印一次，通过每次打印时间间隔是否一致，来判断是否发生Stop The World
 * @author Administrator
 */
public class StopTheWorld {
	private static class PushDataThread extends Thread {
		List<byte[]> list = new LinkedList<>();
		@Override
		public void run() {
			while(true) {
				if(list.size() % 1000 == 0) {
					list.clear();
					System.out.println("list clear");
				}
				for (int i = 0; i < 100; i++) {
					list.add(new byte[512]);
				}
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	private static class TimerThread extends Thread {
		
		@Override
		public void run() {
			long start = System.currentTimeMillis();
			while(true) {
				long times = System.currentTimeMillis() - start;
				System.out.println(times);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
	} 
	
	public static void main(String[] args) {
		new PushDataThread().start();
		new TimerThread().start();
	}
}