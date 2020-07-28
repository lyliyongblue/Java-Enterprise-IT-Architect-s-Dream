package com.yong.file.data.producer;

import com.yong.file.data.common.ICompleted;
import com.yong.file.data.dto.Item;

import java.io.*;
import java.util.function.Consumer;

public class FileItemProducer implements ItemProducer {
    private final File file;
    private final ICompleted done;
    private final Consumer<Item> receiver;

    public FileItemProducer(File file, ICompleted done, Consumer<Item> receiver) {
        this.file = file;
        this.done = done;
        this.receiver = receiver;
    }

    @Override
    public void produce(File file, ICompleted done, Consumer<Item> receiver) {
//        FileLoader fileLoader = FileLoaderFactory.getFileLoader(uri);
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
            } while (line == null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            done.done();
        }
    }

    @Override
    public void run() {
        produce(this.file, this.done, this.receiver);
    }
}
