package com.liyong.reactor.thread;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		//TODO  写日志
		//TODO  发MQ消息
		//TODO  发管理员提醒
		//TODO  executor.getQueue().put(r);  
	}

}
