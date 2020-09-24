### 1、Stream流有哪些方式创建？
通过集合创建流

```java
public class Main {
    public static void main(String args) {
        // 从集合创建
        List<String> list = new ArrayList<>();
        list.stream();
        list.parallelStream();
    }
}
```

通过数组创建流

```java
public class Main {
    public static void main(String args) {
        // 从数组创建
        Arrays.stream(new int[] { 1, 3, 4 });
    }
}
```

通过Stream.of(T ...)创建流

```java
public class Main {
    public static void main(String args) {
        // 创建数字流
        IntStream.of(1, 2, 3);
        IntStream.range(1, 3).forEach(System.out::println);
        IntStream.rangeClosed(1, 3).forEach(System.out::println);
        // 通过of方法创建字符串流
        Stream.of("111", "222", "333").forEach(System.out::println);
    }
}
```

使用random创建一个无限流

```java
public class Main {
    public static void main(String args) {
        IntPredicate filter = i -> i > 0 && i < 100;
        // 使用random创建一个无限流
        new Random().ints().filter(filter).limit(3).forEach(System.out::println);
    }
}
```

通过提供者IntStream.generate(Supplier<T>)创建流

```java
public class Main {
    public static void main(String args) {
        IntPredicate filter = i -> i > 0 && i < 100;
        // 自己产生流
        IntStream.generate(new Random()::nextInt).filter(filter).limit(20).forEach(System.out::println);
    }
}
```

### 2、根据不同的维度，流都有哪些分类？
根据操作的位置分：

- 中间操作：操作方法返回的依然还是个流，就是中间操作
- 终止操作：操作方法返回的不是流，就是终止操作

中间操作：

```java
public class Main {
    public static void main(String args) {
        // limit(int) 截断流,使其元素不超过给定数量
        Stream.generate(() -> Math.random()).limit(5);

        // 使用random创建一个无限流  filter(Predicate<T>)接受一个断言，对流进行过滤
        new Random().ints().filter(filter).limit(3).forEach(System.out::println);
        
        // skip(n) - 跳过元素,返回一个扔掉了前n个元素的流,若流中元素不足n个,则返回一个空流,与limit互补
        
        // distinct - 筛选,通过流所生产元素的hashCode() 和 equals() 去除重复元素
    }
}
```

```java
public class Main {
    public static void main(String args) {
        // 映射
        // map(Function<T, R>) 接收Lambda,将元素转化成其他形式或提取信息,接收一个函数作为参数,改元素会应用到每个元素上,并将其映射成一个新的元素
        // 需求： 将一句话中大于4个字母的单词，转换成小写，输出打印  map将流中的每个元素进行转换
        // 将数组转换成流
        Stream.of(str.split(" "))
            // 过滤成长度大于4的单词
            .filter(s -> s.length() > 4)
            // 将字符串全部小写
            .map(s -> s.toLowerCase())
            // 遍历打印结果
            .forEach(System.out::println);
            
        // flatMap - 接收一个函数作为参数,将流中的的每个值都换成另一个流,然后把所有的流连接成一个流
        // flatMap(Function<? super T, ? extends Stream<? extends R>>)
        // 需求： 将一句话中大于4个字母的单词，拆分成字节，并打印输出统计结果
        // flatMap 将一个元素中的指定的多元素属性拉平
        Stream.of(str.split(" "))
            .filter(s -> s.length() > 4)
            .flatMap(s -> s.chars().boxed())
            .forEach(c -> System.out.println((char)c.intValue()));
    }
}
```

```java
public class Main {
    public static void main(String args) {
        // peek 主要用于调试，它与forEach的区别，在于 peek是中间操作，而forEach是终止操作。
        Stream.of(str.split(" ")).peek(System.out::println).forEach(System.out::println);
    }
}
```

```java
public class Main {
    public static void main(String args) {
        List<String> slist2 = Arrays.asList("bb", "ff", "cc", "dd", "aa", "ee", "gg");
        slist2.stream()
                // 自然排序
                .sorted().forEach(System.out::println);
        
        list.stream()
                // 定制排序
                .sorted((x, y) -> {
                    if (x.getId().equals(y.getId())) {
                        return x.getSaler().compareTo(y.getSaler());
                    } else {
                        return x.getId().compareTo(y.getId());
                    }
            }).forEach(System.out::println);
    }
}
```

中止操作：

```java
public class Main {
    public static void main(String args) {
        // allMatch - 检查是否匹配所有元素
        // anyMatch - 检查是否至少匹配一个元素
        // noneMatch - 检查是否没有匹配所有元素
        // findFirst - 返回第一个元素+
        // findAny - 返回当前流中的任意元素
        // count - 返回流中元素的总和
        // max - 返回流中的最大值
        // min - 返回流中的最小值
        // forEach - 遍历流
        
        allMatch(Predicate<T> p);
        anyMatch(Predicate<T> p);
        noneMatch(Predicate<T> p);
        findFirst();
        findAny();
        count();
        max(Comparator);
        min(Comparator);
        forEach(Consumer<T> c);
    }
}
```

中间操作，又根据数据之间是否存在依赖关系分为：  

- 无状态中间中间操作  
- 有状态中间操作  

无状态操作：  
`map` `flatMap` `filter` `peek`   
这些操作时，流中的数据之间没有依赖关系

有状态操作：  
`distinct` `sorted` `limit/skip`  
去重、排序、截断N个、跳过N个，这些操作都是存在数据之间的依赖关系

> 是否有依赖关系，是判断有状态、无状态的一个标准。  
> 而依赖关系的存在与否，直接影响到流处理过程中是否能使用并行处理。

### 3、Stream流的并行处理，同步处理是什么意思？
并行处理就是多线程同时处理流中的数据，同步处理就是一个线程处理流中的所有数据。  

开启流的并行处理：
```java
public class Main {
    private static void parallelCommonPool() {
        IntStream.rangeClosed(1, 4)
                // 调用parallel产生并行流
                .parallel().peek(StreamDemo5::debug).count();
    }
}
```

多次调用 `parallel` `sequential` 以最后一次调用为准。

```java
public class Main {
    private static void parallelAndSequential() {
        // 多次调用 parallel / sequential, 以最后一次调用为准.
        IntStream.range(1, 1)
                // 调用parallel产生并行流
                .parallel().peek(StreamDemo5::debug)
                // 调用sequential 产生串行流
                .sequential().peek(StreamDemo5::debug2).count();
    }
}
```

> 在不做任何处理时，并行流使用公共的 ForkJoinPool.commonPool 线程池进行执行。默认线程数是当前机器的CPU个数，使用这个属性可以修改默认的线程数

```java
public class Main {
    public static void main(String[] args) {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "20");    
    }
}
```

那怎么让流处理使用自定义的线程池呢？
```java
public class Main {
    private static void parallelNewPool() {
        ForkJoinPool pool = new ForkJoinPool(10);
        pool.submit(() -> {
            IntStream.range(1, 100).parallel().peek(StreamDemo5::debug).count();
        });
    }
}
```

通过new创建一个`ForkJoinPool`
```shell script
  ForkJoinPool pool = new ForkJoinPool(10); 
```

然后将相关处理提交到`ForkJoinPool`中，即可将其放到自定义的线程组中执行。  


### 4、流的执行机制是什么样的？
1. 在多个无状态的操作中，所有操作是链式调用, 一个元素只迭代一次  
2. 每一个中间操作返回一个新的流. 流里面有一个属性sourceStage 指向同一个 地方,就是Head  
3. Head->nextStage->nextStage->... -> null
4. 有状态操作会把无状态操作阶段,单独处理
5. 并行环境下, 有状态的中间操作不一定能并行操作。  
6. `parallel` `sequetial` 这2个操作也是中间操作，也是返回stream，但是他们不创建流, 他们只修改`Head`的并行标志  

```java
package com.liyong.webflux.stream;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * 验证stream运行机制
 * 1. 所有操作是链式调用, 一个元素只迭代一次 
 * 2. 每一个中间操作返回一个新的流. 流里面有一个属性sourceStage 指向同一个 地方,就是Head 
 * 3. Head->nextStage->nextStage->... -> null
 * 4. 有状态操作会把无状态操作阶段,单独处理
 * 5. 并行环境下, 有状态的中间操作不一定能并行操作.
 * 6. parallel/ sequetial 这2个操作也是中间操作(也是返回stream) 但是他们不创建流, 他们只修改 Head的并行标志
 * @author Administrator
 */
public class StreamDemo6RunStream {
	public static void main(String[] args) {
		Random random = new Random();
		Stream<Integer> stream = Stream.generate(random::nextInt)
				// 产生500个 ( 无限流需要短路操作. )
				.limit(10)
				// 第1个无状态操作
				.peek(s -> print("peek: " + s))
				// 第2个无状态操作
				.filter(i -> {
					print("filter: " + i);
					return i > 0;
				})
				// 有状态操作
				.sorted((i1, i2) -> {
					print("排序: " + i1 + ", " + i2);
					return i1.compareTo(i2);
				})
				// 又一个无状态操作
				.peek(s -> {
					print("peek2: " + s);
				}).parallel();
		
		stream.count();
	}

	/** 打印日志并sleep 5 毫秒 */
	public static void print(String s) {
		// System.out.println(s);
		// 带线程名(测试并行情况)
		System.out.println(Thread.currentThread().getName() + " > " + s);
		try {
			TimeUnit.MILLISECONDS.sleep(5);
		} catch (InterruptedException e) {
		}
	}
}
```

### 5、Stream流的收集

1、可以将流收集到集合中，通过Collectors.toCollection(Supplier<T>)提供集合，最终流会收集到对应的集合中返回

```java
public class Main {
	public static void main(String[] args) {
        // 得到所有学生的年龄列表
        // 所有学生的年龄:[10, 9, 8, 13, 7, 13, 13, 9, 6, 6, 14, 13]
        List<Integer> ages = students.stream()
                    .map(Student::getAge)
                    .collect(Collectors.toCollection(ArrayList::new));
        System.out.println("所有学生的年龄:" + ages);
    }
}
```

2、流的信息汇总，Collectors.summarizingInt(ToIntFunction<T>)，可以得到指定汇总字段的相关统计信息 

```java
public class Main {
	public static void main(String[] args) {
        // 统计汇总信息
        // 年龄汇总信息:IntSummaryStatistics{count=12, sum=121, min=6, average=10.083333, max=14}
        IntSummaryStatistics agesSummaryStatistics = students.stream()
                                .collect(Collectors.summarizingInt(Student::getAge));
        System.out.println("年龄汇总信息:" + agesSummaryStatistics);
    }
}
```

3、流的分块，`Collectors.partitioningBy(Predicate<T>)`通过该方法指定一个断言规则，将数据分成两块
```java
public class Main {
	public static void main(String[] args) {
        // 分块
        Map<Boolean, List<Student>> genders = students.stream()
                    .collect(Collectors.partitioningBy(s -> s.getGender() == Gender.FEMALE));
        MapUtils.verbosePrint(System.out, "男女学生列表", genders);
    }
}
```

4、流的分组，Collectors.groupingBy(Function<T, V>)通过该方法将所有T数据，按照V进行分组
```java
public class Main {
	public static void main(String[] args) {
        // 分组
        Map<Grade, List<Student>> grades = students.stream()
                    .collect(Collectors.groupingBy(Student::getGrade));
        MapUtils.verbosePrint(System.out, "学生班级列表", grades);
    }
}
```

5、流分组后，还可以继续在分组的基础上继续进行其他的分组操作（！！！这里还没有理解透！！！！）

```java
public class Main {
	public static void main(String[] args) {
        // 得到所有班级学生的个数
        Map<Grade, Long> gradesCount = students.stream()
                .collect(Collectors.groupingBy(Student::getGrade, Collectors.counting()));
        MapUtils.verbosePrint(System.out, "班级学生个数列表", gradesCount);
    }
}
```

完整的代码示例：

```java
package com.liyong.webflux.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.MapUtils;

public class StreamDemo7Collect {
	public static void main(String[] args) {
		// 测试数据
		List<Student> students = Arrays.asList(
				new Student("小明", 10, Gender.MALE, Grade.ONE),
				new Student("大明", 9, Gender.MALE, Grade.THREE),
				new Student("小白", 8, Gender.FEMALE, Grade.TWO),
				new Student("小黑", 13, Gender.FEMALE, Grade.FOUR),
				new Student("小红", 7, Gender.FEMALE, Grade.THREE),
				new Student("小黄", 13, Gender.MALE, Grade.ONE),
				new Student("小青", 13, Gender.FEMALE, Grade.THREE),
				new Student("小紫", 9, Gender.FEMALE, Grade.TWO),
				new Student("小王", 6, Gender.MALE, Grade.ONE),
				new Student("小李", 6, Gender.MALE, Grade.ONE),
				new Student("小马", 14, Gender.FEMALE, Grade.FOUR),
				new Student("小刘", 13, Gender.MALE, Grade.FOUR));
		
		// 得到所有学生的年龄列表
		// 所有学生的年龄:[10, 9, 8, 13, 7, 13, 13, 9, 6, 6, 14, 13]
		List<Integer> ages = students.stream().map(Student::getAge).collect(Collectors.toCollection(ArrayList::new));
		System.out.println("所有学生的年龄:" + ages);
		
		// 统计汇总信息
		// 年龄汇总信息:IntSummaryStatistics{count=12, sum=121, min=6, average=10.083333, max=14}
		IntSummaryStatistics agesSummaryStatistics = students.stream().collect(Collectors.summarizingInt(Student::getAge));
		System.out.println("年龄汇总信息:" + agesSummaryStatistics);
		
		// 分块
		Map<Boolean, List<Student>> genders = students.stream().collect(Collectors.partitioningBy(s -> s.getGender() == Gender.FEMALE));
		MapUtils.verbosePrint(System.out, "男女学生列表", genders);
		
		// 分组
		Map<Grade, List<Student>> grades = students.stream().collect(Collectors.groupingBy(Student::getGrade));
		MapUtils.verbosePrint(System.out, "学生班级列表", grades);
		
		// 得到所有班级学生的个数
		Map<Grade, Long> gradesCount = students.stream().collect(Collectors.groupingBy(Student::getGrade, Collectors.counting()));
		MapUtils.verbosePrint(System.out, "班级学生个数列表", gradesCount);
	}
}

/** 学生 对象 */
class Student {
	/** 姓名 */
	private String name;
	/** 年龄 */
	private int age;
	/** 性别 */
	private Gender gender;
	/** 班级 */
	private Grade grade;

	public Student(String name, int age, Gender gender, Grade grade) {
		super();
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.grade = grade;
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

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "[name=" + name + ", age=" + age + ", gender=" + gender + ", grade=" + grade + "]";
	}

}

/** 性别 */
enum Gender {
	MALE, FEMALE
}
/** 班级 */
enum Grade {
	ONE, TWO, THREE, FOUR;
}
```

