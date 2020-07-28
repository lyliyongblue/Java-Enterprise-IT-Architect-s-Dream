package com.yong.file.data;

import com.yong.file.data.dto.Item;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * 系统引擎，也是整个系统的调度者，负责调度生成者、消费者
 */
public class Engine {
    private final LinkedList<File> fileList ;
    private final ItemQueue queue = new ItemQueue();

    public Engine(String rootDir) {
        // 从根路径中获取所有文件
        fileList = new FileLoader().findFiles(rootDir);
    }

    public void start(Executor executor) throws InterruptedException {
        CountDownLatch producerLatch = new CountDownLatch(fileList.size());
        CountDownLatch consumerLatch = new CountDownLatch(1);

        // 创建消费者实例
        ItemConsumer consumer = new ItemConsumer(() -> {
            List<Item> items = queue.pop(10);
            if(items.size() == 0 && producerLatch.getCount() == 0 && queue.isEmpty()) {
                items = null;
            }
            return items;
        }, consumerLatch::countDown);
        // 启动消费者线程
        new Thread(consumer).start();

        // 创建生产者，并将生产者放到线程池中执行
        while (!fileList.isEmpty()) {
            File file = fileList.pop();
            ItemProducer producer = new ItemProducer(file, producerLatch::countDown, queue::push);
            executor.execute(producer);
        }

        // 等待生产者，消费者运行完成
        producerLatch.await();
        consumerLatch.await();

        // 按需求对结果进行排序
        List<Item> result = sort(consumer.getResult());
        // 按需求对结果进行打印
        printFormat(result);
    }

    private static void printFormat(List<Item> result) {
        for (Item item : result) {
            System.out.println(String.format("%s, %s, %s", item.getGroupId(), item.getId(), item.getQuota()));
        }
    }

    private static List<Item> sort(List<Item> result) {
        result.sort(((o1, o2) -> {
            if (o1 == null || o2 == null) {
                return 0;
            }
            if (Long.parseLong(o1.getGroupId()) > Long.parseLong(o2.getGroupId())) {
                return 1;
            }
            return -1;
        }));
        return result;
    }

}
