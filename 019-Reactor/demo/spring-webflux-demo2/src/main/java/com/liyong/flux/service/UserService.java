package com.liyong.flux.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.liyong.flux.context.exception.ResourceNotFoundException;
import com.liyong.flux.entity.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {
	 private final Map<String, User> data = new ConcurrentHashMap<>();
	 
	 public Flux<User> getUsers() {
		 return Flux.fromIterable(data.values());
	 }
	 
	 public Flux<User> getByIds(Flux<String> ids) {
		 return ids.flatMap(id -> Mono.justOrEmpty(data.get(id)));
	 }
	 
	 public Mono<User> getById(String id) {
		 return Mono.justOrEmpty(data.get(id))
		 	.switchIfEmpty(Mono.error(new ResourceNotFoundException("id: " + id + " not found")));
	 }
	 
	 public Flux<User> createOrUpdate(Flux<User> users) {
		 return users.doOnNext(user -> data.put(user.getId(), user));
	 }
	 
	 public Mono<User> createOrUpdate(User user) {
		 data.put(user.getId(), user);
		 return Mono.just(user);
	 }
	 
	 public Mono<Boolean> delete(String userId) {
		 User user = data.remove(userId);
		 return Mono.just(user != null);
	 }
}
