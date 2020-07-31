package com.liyong.gc;

import java.lang.ref.WeakReference;

public class TestWeakReference {
	private static class User {
		private Long userId;
		private String name;
		public User(Long userId, String name) {
			super();
			this.userId = userId;
			this.name = name;
		}
	}
	
	public static void main(String[] args) {
		User user = new User(1000L, "Hello");
		// 将user对象进行软引用
		WeakReference<User> userSoft = new WeakReference<>(user);
		// 取消对user对象的引用
		user = null;
		System.out.println(userSoft.get());
		// 手动发起GC调度请求
		System.gc();
		System.out.println(userSoft.get());
	}
}
