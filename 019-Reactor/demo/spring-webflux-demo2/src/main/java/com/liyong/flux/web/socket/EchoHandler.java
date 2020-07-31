package com.liyong.flux.web.socket;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;

import reactor.core.publisher.Mono;

@Component
public class EchoHandler implements WebSocketHandler {
	
	@Override
	public Mono<Void> handle(WebSocketSession session) {
		return session.send(
				session
				.receive()
				.map(msg -> {
					String requestData = msg.getPayloadAsText();
					System.out.println(Thread.currentThread().getName() + "  ->  " + requestData);
					if(requestData.equals("1+1")) {
						return session.textMessage("2");
					} else if(requestData.equals("2+2")) {
						return session.textMessage("4");
					} else if(requestData.equals("quit")) {
						session.close();
						return null;
					}else {
						return session.textMessage("ECHO: " + requestData);
					}
				}));
	}

	
}
