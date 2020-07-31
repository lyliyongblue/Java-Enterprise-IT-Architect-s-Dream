package com.liyong.reactor.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CustomThreadPoolExecutor {
	public static void main(String[] args) {
		// 丢弃老的任务
		RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardOldestPolicy();
		// 将抛出RejectedExecutionException异常
		new ThreadPoolExecutor.AbortPolicy();
		// 调用者自己执行任务
		new ThreadPoolExecutor.CallerRunsPolicy();
		// 丢弃任务
		new ThreadPoolExecutor.DiscardPolicy();
		/*
		 * int corePoolSize 核心线程数
		 * int maximumPoolSize 池中允许的最大线程数 
		 * long keepAliveTime 当线程数大于核心时，这个空闲线程将等待多长时间闲置后被回收
		 * TimeUnit unit 闲置时间单位
		 * BlockingQueue<Runnable> workQueue 任务队列，主要用于设置任务队列深度 
		 * RejectedExecutionHandler handler 任务拒绝策略
		 */
		new ThreadPoolExecutor(8, 64, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10), handler);
	}
}
