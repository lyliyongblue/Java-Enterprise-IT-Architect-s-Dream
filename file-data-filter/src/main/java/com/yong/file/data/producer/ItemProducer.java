package com.yong.file.data.producer;

import com.yong.file.data.common.ICompleted;
import com.yong.file.data.common.IReceiver;
import com.yong.file.data.dto.Item;

import java.io.File;
import java.util.function.Consumer;

public interface ItemProducer extends Runnable {
    void produce(File file, ICompleted done, Consumer<Item> receiver);
}
