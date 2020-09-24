package com.liyong.reactor.test;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
	private final ReentrantLock lock = new ReentrantLock();
	private final Condition getCondition = lock.newCondition();
	
	private final LinkedList<Object> linkedList = new LinkedList<>(); 
	
	private final AtomicInteger counter = new AtomicInteger(2);
	
	public ConnectionPool(int poolSize) {
		if(poolSize > 0) {
			counter.set(poolSize);
		} else {
			poolSize = counter.get();
		}
		for (int i = 0; i < poolSize; i++) {
			linkedList.add(new Object());
		}
	}
	
	private Object getConnection(long timeout) {
		lock.lock();
		try {
			while(counter.get() == 0) {
				try {
					boolean result = getCondition.await(timeout, TimeUnit.SECONDS);
					if(!result) {
						System.err.println(Thread.currentThread().getName() + " timeout: " + timeout);
						return null;
					}
				} catch (InterruptedException e) {
					return null;
				}
			}
			counter.decrementAndGet();
			Object connection = linkedList.pollLast();
			System.out.println(Thread.currentThread().getName() + " get success: " + connection);
			return connection;
		} finally {
			lock.unlock();
		}
	}
	
	private void releaseConnection(Object connection) {
		lock.lock();
		try {
			linkedList.addFirst(connection);
			counter.incrementAndGet();
			getCondition.signal();
			System.out.println(Thread.currentThread().getName() + " release success: " + connection);
		} finally {
			lock.unlock();
		}
	}
	
	static class Worker extends Thread {
		private final ConnectionPool pool;
		public Worker(ConnectionPool pool) {
			this.pool = pool;
		}
		
		@Override
		public void run() {
			while(true) {
				long start = System.currentTimeMillis();
				Object connection = null;
				try {
					connection = pool.getConnection(1);
					if(connection == null) {
						System.err.println(Thread.currentThread().getName() + " 获取连接失败，continue");
						continue;
					}
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					long end = System.currentTimeMillis();
					System.out.println(Thread.currentThread().getName() + " exe: " + (end - start) + "ms");
				} finally {
					if(connection != null) {
						pool.releaseConnection(connection);
					}
				}
			}
		}
	}
	
	
	public static void main(String[] args) {
		ConnectionPool pool = new ConnectionPool(2);
		
		for (int i = 0; i < 20; i++) {
			new Worker(pool).start();
		}
	}
}
