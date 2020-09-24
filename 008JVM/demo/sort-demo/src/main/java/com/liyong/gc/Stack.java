package com.liyong.gc;

public class Stack {
	public Object[] elements;
	/** 指示器，指示当前栈顶的位置 */
	private int size = 0;
	
	private static final int CAP = 16;
	
	public Stack() {
		elements = new Object[CAP];
	}
	
	public void push(Object obj) {
		elements[size] = obj;
		size++;
	}
	
	public Object pop() {
		size--;
		return elements[size];
	}
	
	public static void main(String[] args) throws InterruptedException {
		Stack stack = new Stack();
		Object obj = new Object();
		System.out.println("obj: " + obj);
		stack.push(obj);
		
		// 数据被弹出后，栈中的对象就应该回收
		Object obj2 = stack.pop();
		System.out.println("obj2: " + obj2);
		
		// 但是实际上依旧未被回收，这种就是因为代码问题，而导致了一个对象的内存溢出
		System.out.println(stack.elements[0]);
		Thread.sleep(9999999);
	}
}
