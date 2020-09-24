package com.liyong.reactor.test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Counter {
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private final Lock readLock = readWriteLock.readLock();
	private final Lock writeLock = readWriteLock.writeLock();
	private volatile int count = 0;
	
	public void inc(int index) {
		writeLock.lock();
		try {
			count++;
		} finally {
			writeLock.unlock();
		}
	}
	
	public int get() {
		readLock.lock();
		try {
			return count;
		} finally {
			readLock.unlock();
		}
	}
}
