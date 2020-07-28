package com.yong.file.data;

import com.yong.file.data.dto.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;

/**
 * 数据队列，用于暂存数据，并提供数据的写入/取出的线程安全
 * @author created by li.yong on 2020-07-27 23:11:05
 */
public class ItemQueue {
    /** 设值容量上限，避免程序录入数据过多，导致程序异常 */
    private final LinkedBlockingDeque<Item> queue = new LinkedBlockingDeque<>(100_000);

    public Item pop() {
        try {
            return queue.pop();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public List<Item> pop(int count) {
        List<Item> result = new ArrayList<>();
        if(count <= 0) {
            return result;
        }
        for (int i = 0; i < count; i++) {
            Item item = pop();
            if(item == null) {
                continue;
            }
            result.add(item);
        }
        return result.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    public void push(Item item) {
        try {
            queue.push(item);
        } catch (IllegalStateException ex) {
            // 如果容量满了，则让出当前CPU使用，当前线程获取到CPU后，再重试将数据放到队列中，直到数据放入成功为止
            Thread.yield();
            push(item);
        }
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

}
