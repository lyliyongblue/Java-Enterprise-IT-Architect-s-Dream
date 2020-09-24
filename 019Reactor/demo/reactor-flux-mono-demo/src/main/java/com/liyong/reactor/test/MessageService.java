package com.liyong.reactor.test;

import org.springframework.stereotype.Service;

@Service
public class MessageService {
	public boolean sendMsg(String mobile, String msg) {
		mobile = mobile.intern();
		synchronized (mobile) {
			doValidate();
			doSend();
			doUpdateStatus();
			return true;
		}
	}

	private void doUpdateStatus() {
		
	}

	private void doSend() {
		
	}

	private void doValidate() {
		
	}
}
