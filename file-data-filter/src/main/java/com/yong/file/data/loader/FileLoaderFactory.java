package com.yong.file.data.loader;

/**
 * @author created by li.yong on 2020-07-27 23:41:14
 */
public class FileLoaderFactory {
    public static FileLoader getFileLoader(String uri) {
        if(uri.startsWith("/") || uri.startsWith("file://")) {
            return new FileSystemFileLoader();
        }
        if(uri.startsWith("classpath://")) {
            return new ClasspathFileLoader();
        }
        throw new UnsupportedOperationException("Unsupported uri type");
    }
}
