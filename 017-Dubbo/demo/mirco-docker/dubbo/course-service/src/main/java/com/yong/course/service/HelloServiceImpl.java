package com.yong.course.service;

import com.yong.course.provider.IHelloService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@DubboService(version = "1.0.0")
@Service
public class HelloServiceImpl implements IHelloService {
	@Override
	public String sayHello(String name) {
		return null;
	}
}
