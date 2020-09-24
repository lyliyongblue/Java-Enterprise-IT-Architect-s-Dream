package com.liyong.flux.context.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import com.liyong.flux.web.socket.EchoHandler;

@Configuration
public class WebSocketConfiguration {
	
	@Bean
	public HandlerMapping webSocketMapping(EchoHandler echoHandler) {
		Map<String, WebSocketHandler> map = new HashMap<>();
		map.put("/echo", echoHandler);
		
		SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
		mapping.setOrder(Ordered.HIGHEST_PRECEDENCE);
		mapping.setUrlMap(map);
		return mapping;
	}
	
	@Bean
	public WebSocketHandlerAdapter handlerAdapter() {
		return new WebSocketHandlerAdapter();
	}
}
