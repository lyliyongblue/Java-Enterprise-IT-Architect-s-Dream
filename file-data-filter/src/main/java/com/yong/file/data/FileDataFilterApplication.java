package com.yong.file.data;

import com.yong.file.data.common.IReceiver;
import com.yong.file.data.dto.Item;
import com.yong.file.data.file.FileInfo;
import com.yong.file.data.producer.FileItemProducer;
import com.yong.file.data.producer.ItemProducer;
import com.yong.file.data.queue.ItemQueue;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class FileDataFilterApplication {

    private static final String BASE_DIR = "";

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10,
                10,
                1,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(10),
                new ThreadPoolExecutor.CallerRunsPolicy());

        LinkedList<File> dirList = new LinkedList<>();
        File dir = new File(BASE_DIR);
        dirList.add(dir);

        LinkedList<File> fileList = new LinkedList<>();
        while (dirList.isEmpty()) {
            FileInfo fileInfo = getFile(dirList.pop());
            if(fileInfo == null) {
                continue;
            }
            fileList.addAll(fileInfo.getFiles());
            dirList.addAll(fileInfo.getDirs());
        }
        ItemQueue queue = new ItemQueue();
        CountDownLatch latch = new CountDownLatch(fileList.size());
        while (fileList.isEmpty()) {
            File file = fileList.pop();
            FileItemProducer producer = new FileItemProducer(file, latch::countDown, queue::push);
            executor.execute(producer);
        }

    }

    public static FileInfo getFile(File dir) {
        if (!dir.isDirectory()) {
            return null;
        }
        FileInfo fileInfo = new FileInfo();
        File[] dirFiles = dir.listFiles();
        if (dirFiles == null) {
            return null;
        }
        List<File> files = new ArrayList<>();
        List<File> dirs = new ArrayList<>();
        for (File file : dirFiles) {
            if(file.isDirectory()) {
                dirs.add(file);
            } else {
                files.add(file);
            }
        }
        fileInfo.setDirs(dirs);
        fileInfo.setFiles(files);
        return fileInfo;
    }


}
