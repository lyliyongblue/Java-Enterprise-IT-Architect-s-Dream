package com.yong.spring.data.repository.aggregate;


import org.springframework.context.ApplicationEvent;

public class TestEvent extends ApplicationEvent {

    public TestEvent(Object source) {
        super(source);
    }
}
