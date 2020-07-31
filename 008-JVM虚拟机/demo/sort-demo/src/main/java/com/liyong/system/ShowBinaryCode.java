package com.liyong.system;

public class ShowBinaryCode extends AbstractHuman implements IGreeting {

	private static final String DEFAULT_NAME = "Hello World";
	
	private static final int ADULT_AGE = 18;
	
	private static final long DEFAULT_DISTANCE = 1000L;
	
	private static final double DEFAULT_SALARY = 3000.01;
	
	private static final float DEFAULT_CONSUME = 1000.02F;
	
	private static final IGreeting DEFAULT_GREETING = new IGreeting() {
		@Override
		public void work(String task1, String task2) {
		}
		
		@Override
		public void sayHello(String msg) {
		}
	};
	
	
	private String name;
	private int age;

	public ShowBinaryCode() {
	}

	public ShowBinaryCode(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

	public static void main(String[] args) {
		ShowBinaryCode code = new ShowBinaryCode();
		code.setName("Li");
		code.setAge(100);
		System.out.println("code: " + code);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	private long distance;
	private Object body;
	private float consume;
	private double salary;

	public long getDistance() {
		return distance;
	}

	public void setDistance(long distance) {
		this.distance = distance;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public float getConsume() {
		return consume;
	}

	public void setConsume(float consume) {
		this.consume = consume;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	@Override
	public void sayHello(String msg) {
		System.out.println("msg: " + msg);
	}

	@Override
	public void work(String task1, String task2) {
		System.out.println(task1 + " -->  " + task2);
	}

	@Override
	protected void doSomething() {
		System.out.println("do something");
	}
}
