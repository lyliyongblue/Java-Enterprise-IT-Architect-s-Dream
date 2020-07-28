package com.yong.file.data;

import com.yong.file.data.dto.FileInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 文件加载器，加载指定目录中的所有文件
 */
public class FileLoader {
    /**
     * 从根路径中获取所有文件
     * @param rootDir 搜索文件根路径
     * @return 根目录/子目录中的所有文件
     */
    public LinkedList<File> findFiles(String rootDir) {
        LinkedList<File> dirList = new LinkedList<>();
        File dir = new File(rootDir);
        dirList.add(dir);

        LinkedList<File> fileList = new LinkedList<>();
        // 用循环而不用递归，减少方法栈深度
        while (!dirList.isEmpty()) {
            FileInfo fileInfo = getFile(dirList.pop());
            if(fileInfo == null) {
                continue;
            }
            fileList.addAll(fileInfo.getFiles());
            dirList.addAll(fileInfo.getDirs());
        }
        return fileList;
    }

    private FileInfo getFile(File dir) {
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
