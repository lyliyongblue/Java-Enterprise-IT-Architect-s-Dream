### 1、JDK自带了哪些通用函数式接口？
```java
public class Test() {
    public void test() {
        // 断言接口：接受一个入参，返回一个Boolean
        Predicate<Integer> predicate = data -> data > 0;
        
        // JDK自带消费者函数：接受一个入参，无返回值
        Consumer<String> consumer = str -> System.out.println(str);
        
        // JDK自带Function函数：接受一个入参，返回一个结果
        Function<Integer, Integer> function = data -> data * 2;
        
        // JDK自带提供者Supplier函数：无入参，返回一个数据
        Supplier<Double> supplier = () -> 20.00;
        
        // JDK自带一元函数UnaryOperator函数：一个入参，一个返回值，并且入参、返回值类型一致
        UnaryOperator<String> unaryOperator = str -> str.substring(str.length() / 2);
        
        // JDK自带BiFunction函数：2个入参，一个返回值
        BiFunction<Integer, Double, String> biFunction = (data1, data2) -> data1 + " -> " + data2;
        
        // JDK自带二元函数BinaryOperator：2个入参，一个返回值， 并且3个数据类型一致 
        BinaryOperator<Integer> binaryOperator = Integer::sum;
    }
}
```

### 2、Jdk函数接口的优势是什么？
- 1、不需要定义更多的接口
- 2、函数接口支持级联操作

```java
public class Test() {
    public void test() {
        BinaryOperator<Integer> binaryOperator = Integer::sum;
        // 级联操作
        BiFunction<Integer, Integer, String> binaryOperatorFunction = binaryOperator.andThen(total -> total + " 元");
        System.out.println(binaryOperatorFunction.apply(12, 15));
    }
}
```

### 3、方法引用，在java中怎么进行方法引用？
静态方法应用，使用类名直接应用方法

```java
public class Test() {
    public void test() {
        // 静态方法引用，通过类名直接指定方法
        Consumer<String> println = System.out::println;
        println.accept("Hello Method Refrence");
        
        // 静态方法引用，引用自定义的静态方法
        Person person = new Person("小可爱", 100);
        Consumer<Person> show = Person::show;
        Consumer<Person> showName = Person::showName;
        show.accept(person);
        showName.accept(person);
    }
}
```

成员方法引用，通过具体实例进行引用

```java
public class Test() {
    public void test() {
        // 创建实例
        Person person = new Person("小可爱", 100);
        // 成员方法引用，使用对象进行引用
        Consumer<Double> add = person::add;
        add.accept(20d);
    }
}
```

无论是静态方法引用，还是成员方法引用，我们都可以使用一个函数接口来对其进行引用，应用规则：

- 一个入参       无返回值                                                                                  则该方法就是一个Consumer
- 一个入参       一个Boolean返回值                                                           则该方法就是Predicate
- 一个入参       一个返回值                                                                             那该方法就是一个Function
- 无入参            一个返回值                                                                             那么该方法就是Supplier
- 一个入参       一个返回值，并且入参返回值类型一样                  那么该方法就是一个UnaryOperator
- 两个入参       一个返回值                                                                             那么该方法就是一个BiFunction
- 两个入参       一个返回值 ，并且入参返回值类型一样                 那么该方法就是一个BinaryOperator

### 4、函数的级联表达式和柯里化是什么？
级联表达式，就是函数的返回值还是函数。  
柯里化： 通估计级联表达式，将多个参数的函数转换为只有一个参数的函数，就叫柯里化。  
柯里化的目的：函数标准化  
级联表达式也高阶函数：返回函数的函数  

```java
public class CurryDemo {
	public static void main(String[] args) {
		// Function<x, Function<y, x + y>> 实现x+y的级联表达式
		Function<Integer, Function<Integer, Integer>> function = x -> y -> x + y;
		System.out.println(function.apply(10).apply(22));
		
		// Function<x, Function<y, Function<z, x + y + z>> 实现x+y+y的级联表达式
		Function<Integer, Function<Integer, Function<Integer, Integer>>> function3 = x -> y -> z -> x + y + z;
		System.out.println(function3.apply(1).apply(2).apply(3));
	}
}
```

### 5、怎么定义函数接口？怎么引用自定义的函数接口？
定义一个函数接口，通过@FunctionalInterface注解，指明该接口为函数接口，函数接口只允许有且只有一个接口方法。使用@FunctionalInterface后，编译器就会检查代码格式。

```java
@FunctionalInterface
public interface Couter {
	int incr(int init, int delay);
}
```

引用函数接口，引用函数接口时，可以理解成是对接口的一种实现语法，并获取到接口实例。可以通过该实例直接调用接口方法了。

```java
public class LambdaDemo {
	public static void main(String[] args) {
		Couter couter = (init, delay) -> init + delay;
		System.out.println(couter.incr(10, 2));
	}
}
```
