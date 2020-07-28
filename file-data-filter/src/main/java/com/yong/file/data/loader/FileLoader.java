package com.yong.file.data.loader;

import java.io.InputStream;

public interface FileLoader {
    InputStream load(String uri);
}
