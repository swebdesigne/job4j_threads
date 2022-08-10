package ru.job4j.concurrent.parser;

import java.io.*;

public final class SaveToFile implements ISaveContent {
    @Override
    public void save(File file, String content) {
        try (OutputStream o = new FileOutputStream(file);
             BufferedOutputStream bfr = new BufferedOutputStream(o)
        ) {
            for (int i = 0; i < content.length(); i += 1) {
                bfr.write(content.charAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
