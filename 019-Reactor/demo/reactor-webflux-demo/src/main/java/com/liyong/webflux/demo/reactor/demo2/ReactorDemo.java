package com.liyong.webflux.demo.reactor.demo2;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactorDemo {
	public Flux<String> ss() {
		return ifhrIds().flatMap(id -> {
			Mono<String> nameTask = ifhrName(id);
			Mono<Integer> statTask = ifhrStat(id);
			return nameTask.zipWith(statTask, (name, stat) -> "Name " + name + " has stats " + stat);
		});
	}

	private Mono<Integer> ifhrStat(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	private Mono<String> ifhrName(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	private Flux<String> ifhrIds() {
		// TODO Auto-generated method stub
		return null;
	}
}
