package ru.job4j.concurrent.parser.file;

import java.io.File;

public interface ISaveContent {
    void save(File file, String content);
}
