package com.yong.file.data;

import java.util.concurrent.*;

public class Main {

    private static final String BASE_DIR = "/data/softwares/log/source";

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 1, TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(10),
                new ThreadPoolExecutor.CallerRunsPolicy());

        // 启动系统引擎，开始任务
        new Engine(BASE_DIR).start(executor);

        // 关闭线程池
        executor.shutdown();
    }
}
