package com.liyong.webflux.reactor;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

public class StepVerifierDemo {
	@Test
	public void testStepVerifier() {
		/*
		 * StepVerifier 的作用是可以对序列中包含的元素进行逐一验证。
		 * 通过 StepVerifier.create()方法对一个流进行包装之后再进行验证。
		 * expectNext()方法用来声明测试时所期待的流中的下一个元素的值，而 verifyComplete()方法则验证流是否正常结束。
		 * 类似的方法还有 verifyError()来验证流由于错误而终止。
		 */
		StepVerifier.create(Flux.just("a", "b"))
			.expectNext("a")
			.expectNext("b")
			.verifyComplete();
	}
	
	@Test
	public void testWithVirtualTime() {
		/*
		 * 操作测试时间
		 * 有些序列的生成是有时间要求的，比如每隔 1 分钟才产生一个新的元素。
		 * 在进行测试中，不可能花费实际的时间来等待每个元素的生成。
		 * 此时需要用到 StepVerifier 提供的虚拟时间功能。
		 * 通过 StepVerifier.withVirtualTime()方法可以创建出使用虚拟时钟的 StepVerifier。
		 * 通过 thenAwait(Duration)方法可以让虚拟时钟前进。
		 */
		StepVerifier.withVirtualTime(() -> {
			return Flux.interval(Duration.ofHours(4), Duration.ofDays(1)).take(2);
		}).expectSubscription()
		.expectNoEvent(Duration.ofHours(4))
		.expectNext(0L)
		.thenAwait(Duration.ofDays(1))
		.expectNext(1L)
		.verifyComplete();
	}
	
	public static void main(String[] args) {
		new StepVerifierDemo().testTestPublisher();
	}
	
	@Test
	public void testTestPublisher() {
		final TestPublisher<String> testPublisher = TestPublisher.create();
		testPublisher.next("a");
		testPublisher.next("b");
		testPublisher.complete();
		
		StepVerifier.create(testPublisher)
	        .expectNext("a")
	        .expectNext("b")
	        .expectComplete();
	}
}
