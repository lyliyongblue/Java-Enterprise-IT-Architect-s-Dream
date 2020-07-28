package com.yong.file.data;

/**
 * 用于与调度者通信，消费者、生产者均通过该函数想调度者发送处理完成信号
 */
@FunctionalInterface
public interface ICompleted {
    void done();
}
