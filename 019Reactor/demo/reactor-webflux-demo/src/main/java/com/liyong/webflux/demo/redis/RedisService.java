package com.liyong.webflux.demo.redis;

import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RedisService {
	
	private final ReactiveStringRedisTemplate redisTempate;
	
	public RedisService(ReactiveStringRedisTemplate redisTempate) {
		this.redisTempate = redisTempate;
	}
	
	public Flux<String> getDataList(String id) {
		return redisTempate.opsForList().range(getKey(id), 0, 10);
	}
	
	public Mono<Boolean> save(String id, String data) {
		String key = getKey(id);
		return redisTempate.hasKey(getKey(id)).flatMap(result -> {
			if(!result) {
				throw new RuntimeException("不存在");
			}
			return redisTempate.opsForList().rightPush(key, data);
		}).flatMap(result -> Mono.just(Boolean.TRUE));
	}
	
	private String getKey(String id) {
		return "flux::" + id;
	}
}
