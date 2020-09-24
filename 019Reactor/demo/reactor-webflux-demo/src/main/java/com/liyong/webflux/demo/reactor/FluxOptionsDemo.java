package com.liyong.webflux.demo.reactor;

import java.time.Duration;
import java.util.Arrays;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class FluxOptionsDemo {
	public static void main(String[] args) throws Exception {
		Hooks.onOperatorDebug();
		
		// combineLatest 操作符把所有流中的最新产生的元素合并成一个新的元素，作为返回结果流中的元素。
		// 只要其中任何一个流中产生了新的元素，合并操作就会被执行一次，结果流中就会产生新的元素。
		// 输出结果为： [0, 0][0, 1][1, 1][1, 2][2, 2][2, 3][3, 3][3, 4][4, 4]
		Flux.combineLatest(
		        Arrays::toString,
		        Flux.interval(Duration.ofMillis(1000)).take(5),
		        Flux.interval(Duration.ofMillis(500), Duration.ofMillis(1000)).take(5)
		).log("Interval").checkpoint("test").subscribe(System.out::print);
		
		System.in.read();
	}
	
	static class ResponseBuilder {
		public String build(String data) {
			return "[<-" + data + "->]";
		}
	}
	
	static class LocalDbService {
		Mono<Boolean> save(int data) {
			// 支持响应处理的数据库进行存盘操作
			return Mono.just(Boolean.TRUE);
		}
	}
	
	static class ServiceA {
		Mono<String> queryAa() {
			// 响应式操作，比如向服务A发起了一个查询获取到查询结果
			return Mono.just("AAAAA");
		}
	}
	
	static class ServiceB {
		Mono<String> queryBb() {
			// 响应式操作，比如向服务B发起了一个查询获取到查询结果
			return Mono.just("BBBBB");
		}
	}
	
	public void backUp() throws Exception {
		/*
		 * 这两个操作符的作用是把当前流中的元素收集到集合中，并把集合对象作为流中的新元素。
		 * 在进行收集时可以指定不同的条件：所包含的元素的最大数量和收集的时间。方法 buffer()仅使用一个条件，而 bufferTimeout()可以同时指定两个条件。
		 * 指定时间间隔时可以使用 Duration 对象或毫秒数，即使用 bufferMillis()或 bufferTimeoutMillis()两个方法。
		 * 除了元素数量和时间之外，还可以通过 bufferUntil 和 bufferWhile 操作符来进行收集。
		 * 这两个操作符的参数是表示每个集合中的元素所要满足的条件的 Predicate 对象。
		 * bufferUntil 会一直收集直到 Predicate 返回为 true。
		 * 使得 Predicate 返回 true 的那个元素可以选择添加到当前集合或下一个集合中；
		 * bufferWhile 则只有当 Predicate 返回 true 时才会收集，一旦值为 false，会立即开始下一次收集
		 */
		// buffer bufferTimeout bufferUntil bufferUntil bufferWhile 共同的目的就是对数据进行缓冲，让一次消费处理更多的数据
		System.out.println("-----------Flux.create buffer-----------");
		Flux.create(sink -> {
			for (int i = 0; i < 20; i++) {
		        sink.next(i);
		        try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		    }
			sink.complete();
		}).buffer(5).subscribe(System.out::println);
		
		Flux.range(1, 100).buffer(10).subscribe(System.out::println);
		
		System.out.println("-----------Flux.create bufferTimeout-----------");
		Flux.create(sink -> {
			for (int i = 0; i < 20; i++) {
		        sink.next(i);
		        try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		    }
			sink.complete();
		}).bufferTimeout(5, Duration.ofSeconds(2)).subscribe(System.out::println);
		
		System.out.println("-----------Flux.range bufferWhile-----------");
		Flux.range(1, 10).bufferWhile(data -> data%4 == 0).subscribe(System.out::println);
		
		System.out.println("-----------Flux.range bufferUntil-----------");
		Flux.range(1, 10).bufferUntil(data -> data%4 == 0).subscribe(System.out::println);
		
		
		
		
		/*
		 * window 操作符的作用类似于 buffer，所不同的是 window 操作符是把当前流中的元素收集到另外的 Flux 序列中，
		 * 因此返回值类型是 Flux<Flux>。
		 * window可以指定个数截取，也可以根据时间片进行截取（时间窗口）
		 */
		System.out.println("-----------Flux.range window-----------");
		//  subscribe里面订阅到的数据其实是，Flux<T>，所以我们在该对象上再次订阅了
		Flux.range(1, 100).window(20).subscribe(flux -> flux.subscribe(System.out::println));
		Flux.interval(Duration.ofMillis(100)).window(Duration.ofMillis(1001)).subscribe(System.out::println);
		// 由于上面一行代码我们没有Limit，并且它是异步处理发布订阅，这里通过控制台输入来挂住主线程，让其可以持续生产、消费数据
		System.in.read();
		

		
		
		
		
		// filter 对流中包含的元素进行过滤，只留下满足 Predicate 指定条件的元素
		System.out.println("-----------Flux.range filter-----------");
		Flux.range(1, 10).filter(i -> i % 2 == 0).subscribe(System.out::println);
		
		
		
		
		
		
		
		/*
		 * zipWith 操作符把当前流中的元素与另外一个流中的元素按照一对一的方式进行合并。
		 * 在合并时可以不做任何处理，由此得到的是一个元素类型为 Tuple2 的流；
		 * 也可以通过一个 BiFunction 函数对合并的元素进行处理，所得到的流的元素类型为该函数的返回值。
		 */
		System.out.println("-----------Flux zipWith-----------");
		/*
		 * [a,c]
		 * [b,d]
		 */
		Flux.just("a", "b").zipWith(Flux.just("c", "d")).subscribe(System.out::println);
		
		/*
		 * a -> c
		 * b -> d
		 */
		System.out.println("-----------Flux zipWith combine-----------");
		Flux.just("a", "b").zipWith(Flux.just("c", "d", "e"), (data1, data2) -> {
			return data1 + " -> " + data2;
		}).subscribe(System.out::println);
		
		Mono.just(new ResponseBuilder())
			//.publishOn(Schedulers.elastic())
			.subscribeOn(Schedulers.elastic())
			.zipWith(new ServiceA().queryAa())
			.zipWith(new ServiceB().queryBb(), (builderAndResultAa, resultBb) -> {
				// 模拟经过一系列的处理，最终得到我们需要的数据对象，并返回
				ResponseBuilder builder = builderAndResultAa.getT1();
				String result1 = builder.build(builderAndResultAa.getT2());
				String result2 = builder.build(resultBb);
				System.out.println(result1 + result2);
				System.out.println("数据校验，数据处理完成");
				return 100;
			}).subscribe((data) -> {
				// 将数据存盘
				new LocalDbService().save(data);
				System.out.println("存盘完成");
			}, err -> {
				
			}, () -> {
				System.out.println("通知完成");
				// 处理完成后发消息到其他系统进行通知
			});
		
		
		
		
		
		
		
		
		/*
		 * take 系列操作符用来从当前流中提取元素
		 */
		// take 按照指定的数量或时间间隔来提取
		System.out.println("-----------Flux range take-----------");
		Flux.range(1, 1000).take(10).subscribe(System.out::println);
		
		// 提取流中的最后 N 个元素
		System.out.println("-----------Flux range takeLast-----------");
		Flux.range(1, 1000).takeLast(10).subscribe(System.out::println);
		
		// 当 Predicate 返回 true 时才进行提取
		System.out.println("-----------Flux range takeWhile-----------");
		Flux.range(1, 1000).takeWhile(i -> i < 10).subscribe(System.out::println);
		
		// 提取元素直到 Predicate 返回 true
		System.out.println("-----------Flux range takeUntil-----------");
		Flux.range(1, 1000).takeUntil(i -> i == 10).subscribe(System.out::println);
		
		// 提取元素直到另外一个流开始产生元素
		// takeUntilOther(Publisher<?> other)
		System.in.read();
		
		
		
		
		
		
		/*
		 * reduce 和 reduceWith 操作符对流中包含的所有元素进行累积操作，得到一个包含计算结果的 Mono 序列。
		 * 累积操作是通过一个 BiFunction 来表示的。
		 * reduce 在操作时可以指定一个初始值。如果没有初始值，则序列的第一个元素作为初始值。
		 * reduceWith 必须指定初始值
		 */
		System.out.println("-----------Flux range reduce-----------");
		Flux.range(1, 100).reduce((x, y) -> x + y).subscribe(System.out::println);
		
		Flux.range(1, 100).reduceWith(() -> 10000, (x, y) -> x + y).subscribe(System.out::println);
		
		System.in.read();
		
		
		
		
		
		
		/*
		 * merge 和 mergeSequential 操作符用来把多个流合并成一个 Flux 序列。
		 * 不同之处在于 merge 按照所有流中元素的实际产生顺序来合并;
		 * 而 mergeSequential 则按照所有流被订阅的顺序，以流为单位进行合并。
		 */
		/*
		 * 说明： 进行合并的流都是每隔 100 毫秒产生一个元素，不过第二个流中的每个元素的产生都比第一个流要延迟 50 毫秒。
		 * 在使用 merge 的结果流中，来自两个流的元素是按照时间顺序交织在一起；
		 * 而使用 mergeSequential 的结果流则是首先产生第一个流中的全部元素，再产生第二个流中的全部元素。
		 */
		// 0011223344
		Flux.merge(
				Flux.interval(Duration.ofMillis(0), Duration.ofMillis(100)).take(5), 
				Flux.interval(Duration.ofMillis(50), Duration.ofMillis(100)).take(5)
			).subscribe(System.out::print);
			
		// 0123401234
		Flux.mergeSequential(
				Flux.interval(Duration.ofMillis(0), Duration.ofMillis(100)).take(5), 
				Flux.interval(Duration.ofMillis(50), Duration.ofMillis(100)).take(5)
			).subscribe(System.out::print);
		
		System.in.read();
		
		
		
		
		
		
		
		// map 用于对流数据进行处理，如下代码：对数值转换成字符串，并进行拼接
		Flux.just(6, 10).map(i -> "data: " + String.valueOf(i)).subscribe(System.out::println);
		
		// flatMap 和 flatMapSequential 操作符用于将流拉平， 
		// 拉平：每个数据，经过处理后，可以产生N个元素返回，通过flatMap和flatMapSequential后，就可以将这每一个N合并成一个流
		// 在使用 flatMap的结果流中，来自两个流的元素是按照时间顺序交织在一起；
		// 而使用 flatMapSequential 的结果流则是首先产生第一个流中的全部元素，再产生第二个流中的全部元素。
		// 输出结果： 001122334456789
		Flux.just(5, 10)
			.flatMap(x -> Flux.interval(Duration.ofMillis(x * 10), Duration.ofMillis(100)).take(x))
			.subscribe(System.out::print);
		
		// 输出结果： 012340123456789
		Flux.just(5, 10)
			.flatMapSequential(x -> Flux.interval(Duration.ofMillis(x * 10), Duration.ofMillis(100)).take(x))
			.subscribe(System.out::print);
		
		System.in.read();
		
		
		
		/*
		 * concatMap操作符的作用也是把流中的每个元素转换成一个流，再把所有流进行合并。
		 * 与 flatMap不同的是，concatMap 会根据原始流中的元素顺序依次把转换之后的流进行合并；
		 * 与 flatMapSequential 不同的是，concatMap 对转换之后的流的订阅是动态进行的，而 flatMapSequential 在合并之前就已经订阅了所有的流。
		 */
		Flux.just(5, 10)
        	.concatMap(x -> Flux.interval(Duration.ofMillis(x * 100), Duration.ofMillis(1000)).take(x))
        	.subscribe(System.out::print);
		System.in.read();

		
		
		
		
		
		
		// combineLatest 操作符把所有流中的最新产生的元素合并成一个新的元素，作为返回结果流中的元素。
		// 只要其中任何一个流中产生了新的元素，合并操作就会被执行一次，结果流中就会产生新的元素。
		Flux.combineLatest(
		        Arrays::toString,
		        Flux.interval(Duration.ofMillis(1000)).take(5),
		        Flux.interval(Duration.ofMillis(500), Duration.ofMillis(1000)).take(5)
		).subscribe(System.out::print);
		
		
		
		
		
		
		
		
		
		
		
	}
}
