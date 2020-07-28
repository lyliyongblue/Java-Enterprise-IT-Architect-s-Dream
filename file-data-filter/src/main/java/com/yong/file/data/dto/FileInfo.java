package com.yong.file.data.dto;

import java.io.File;
import java.util.List;

public class FileInfo {
    private List<File> files;
    private List<File> dirs;

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public List<File> getDirs() {
        return dirs;
    }

    public void setDirs(List<File> dirs) {
        this.dirs = dirs;
    }
}
