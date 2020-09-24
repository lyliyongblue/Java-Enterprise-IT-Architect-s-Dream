package com.liyong.flux.service;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class HelloService {
	
	private final RemoteHelloService remoteHelloService;
	
	private final DataWrapService dataWrapService;
	
	public HelloService(RemoteHelloService remoteHelloService,
			DataWrapService dataWrapService) {
		this.dataWrapService = dataWrapService;
		this.remoteHelloService = remoteHelloService;
	}
	
	public Mono<String> getData(String uuid, int delay) {
		System.out.println("step0: " + Thread.currentThread().getName() + "  uuid: " + uuid);
		Mono.just(dataWrapService)
			.zipWith(remoteHelloService.getData("Hello", delay))
			.zipWith(remoteHelloService.getData("Hello", delay))
			.zipWith(remoteHelloService.getData("Hello", delay));
		
		return Flux.concat(
					remoteHelloService.getData("Hello", delay / 3), 
					remoteHelloService.getData("Hello", delay / 2),
					remoteHelloService.getData("Hello", delay)
				).reduce((data1, data2) -> data1 + " || " + data2);
	}
			
}
