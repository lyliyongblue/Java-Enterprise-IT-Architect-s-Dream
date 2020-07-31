package com.liyong.flux.web;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liyong.flux.entity.User;
import com.liyong.flux.service.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public Flux<User> getUsers() {
		System.out.println("11: " + Thread.currentThread().getName());
		return userService.getUsers().filter(user -> {
			user.setEmail(user.getEmail() + " -->");
			System.out.println("22: " + Thread.currentThread().getName());
			return true;
		});
	}
	
	@GetMapping("/{id}")
	public Mono<User> getById(@PathVariable("id") String id) {
		return userService.getById(id);
	}
	
	@PostMapping
	public Mono<User> create(@RequestBody User user) {
		return userService.createOrUpdate(user);
	}
	
	@PutMapping("/{id}")
	public Mono<User> update(@PathVariable("id") String id, @RequestBody User user) {
		Assert.notNull(user, "用户信息不能为空");
		user.setId(id);
		return userService.createOrUpdate(user);
	}
	
	@DeleteMapping("/{id}")
	public Mono<Boolean> delete(@PathVariable("id") String userId) {
		return userService.delete(userId);
	}

}
