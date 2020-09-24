package com.liyong.webflux.demo.lambda.method;

import java.util.function.Consumer;

/**
 * 2、方法引用
 * @author created by li.yong on 2020-4-1 23:08:29
 */
public class MethodRefrenceDemo {

	public static void main(String[] args) {
		// 静态方法引用，通过类名直接指定方法
		Consumer<String> println = System.out::println;
		println.accept("Hello Method Refrence");
		
		// 静态方法引用，引用自定义的静态方法
		Person person = new Person("小可爱", 100);
		Consumer<Person> show = Person::show;
		Consumer<Person> showName = Person::showName;
		show.accept(person);
		showName.accept(person);
		
		// 成员方法引用，使用对象进行引用
		Consumer<Double> add = person::add;
		add.accept(Double.valueOf(20));
		show.accept(person);
		
		/*
		 * 总结： 在做方法引用时
		 * 如果方法是一个入参，无返回值  -> 则该方法就是一个Consumer
		 * 如果方法是一个入参，一个Boolean返回值 -> 则该方法就是Predicate
		 * 如果方式是一个入参，一个返回值 -> 那该方法就是一个Function
		 * 如果方法无入参，一个返回值 -> 那么该方法就是Supplier
		 * 如果方法是一个入参，一个返回值，并且入参返回值类型一样 -> 那么该方法就是一个UnaryOperator
		 * 如果方式是两个入参，一个返回值 -> 那么该方法就是一个BiFunction
		 * 如果方式是两个入参，一个返回值 ，并且入参返回值类型一样 -> 那么该方法就是一个BinaryOperator
		 */
	}

	static class Person {
		/** 姓名 */
		private String name;
		/** 薪水 */
		private double salary;

		public Person(String name, double salary) {
			this.name = name;
			this.salary = salary;
		}

		/**
		 * 加工资
		 * 
		 * @param monery 加钱的金额
		 */
		public void add(double monery) {
			this.salary += monery;
		}

		/**
		 * 传入【人】实例，打印相关信息
		 * 
		 * @param person 传入【人】实例
		 */
		public static void show(Person person) {
			System.out.println(person.toString());
		}
		
		public static void showName(Person person) {
			System.out.println("my name is " + person.name);
		}
		
		@Override
		public String toString() {
			return "Person [name=" + name + ", salary=" + salary + "]";
		}
	}

}
