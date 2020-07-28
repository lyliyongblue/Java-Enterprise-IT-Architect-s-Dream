package com.yong.file.data;

import com.yong.file.data.dto.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 消费者通过supplier获取数据，进行消费，屏蔽了调度者具体数据存储逻辑； 通过done通知调度者，完成消费，屏蔽了调度者的多线程协作逻辑；
 * 消费者仅关注数据消费即可
 */
public class ItemConsumer implements Runnable {
    private final Map<String, Item> result = new HashMap<>();
    private final Supplier<List<Item>> supplier;
    private final ICompleted done;

    /**
     * 消费者通过supplier来提供数据，消费完数据后，调用done来通知调度者，数据消费完成
     * @param supplier 数据提供者，为消费者提供数据
     * @param done 消费完成通知
     */
    public ItemConsumer(Supplier<List<Item>> supplier, ICompleted done) {
        this.supplier = supplier;
        this.done = done;
    }
    public void consume() {
        List<Item> items = supplier.get();
        while (items != null) {
            for (Item item : items) {
                String groupId = item.getGroupId();
                Item minItem = result.get(groupId);
                if(minItem == null || item.getQuota() < minItem.getQuota()) {
                    result.put(groupId, item);
                }
            }
            items = supplier.get();
        }
        done.done();
    }

    public List<Item> getResult() {
        return new ArrayList<>(result.values());
    }

    @Override
    public void run() {
        consume();
    }
}
