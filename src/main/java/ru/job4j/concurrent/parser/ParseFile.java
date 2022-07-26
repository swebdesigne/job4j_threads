package ru.job4j.concurrent.parser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Predicate;

/**
 * This class parsing the file
 *
 * @author Sivolobov Igor
 * @version 1.0
 */
public final class ParseFile {
    private final File file;

    public ParseFile(String file) {
        this.file = new File(file);
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent() {
        return getContentFromFile(x -> true);
    }

    public synchronized String getContentWithoutUnicode() {
        return getContentFromFile(x -> x < 0X80);
    }

    /**
     * Read from file and give away to required method by predicate
     *
     * @param filter predicate, that checking an input data on UNICODE
     * @return String
     */
    private String getContentFromFile(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream bfr = new BufferedInputStream(new FileInputStream(file), 200)) {
            int data;
            while ((data = bfr.read()) != -1) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
