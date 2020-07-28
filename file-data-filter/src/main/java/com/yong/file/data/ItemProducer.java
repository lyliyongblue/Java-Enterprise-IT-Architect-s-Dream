package com.yong.file.data;

import com.yong.file.data.dto.Item;

import java.io.*;
import java.util.function.Consumer;

/**
 * 说明： 该生产者，通过done和receiver屏蔽了调度者的实现细节，生产者只需要关注生成item即可
 */
public class ItemProducer implements Runnable {
    private final File file;
    private final ICompleted done;
    private final Consumer<Item> receiver;

    /**
     * 接收一个文件，用于产生Item实例，生成好的实例，通过调用receiver回传数据给调度者
     * @param file 数据来源文件
     * @param done 文件的数据处理完成，调用done来通知调度者处理完成
     * @param receiver 产生Item对象后，通过调用receiver回传数据给调度者
     */
    public ItemProducer(File file, ICompleted done, Consumer<Item> receiver) {
        this.file = file;
        this.done = done;
        this.receiver = receiver;
    }

    public void produce() {
        try (InputStream in = new FileInputStream(file);
             BufferedInputStream bufIn = new BufferedInputStream(in);
             InputStreamReader reader = new InputStreamReader(bufIn);
             BufferedReader bufReader = new BufferedReader(reader)) {
            String line = bufReader.readLine();
            do {
                if (line == null) {
                    break;
                }
                String[] dataArr = line.split(",");
                if (dataArr.length == 3) {
                    Item item = new Item(dataArr[0], dataArr[1], Float.parseFloat(dataArr[2]));
                    receiver.accept(item);
                }
                line = bufReader.readLine();
            } while (line != null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            done.done();
        }
    }

    @Override
    public void run() {
        produce();
    }
}
