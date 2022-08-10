package ru.job4j.concurrent.parser.file;

import java.io.*;
import java.util.function.Predicate;

/**
 * This class parsing the file
 *
 * @author Sivolobov Igor
 * @version 1.0
 */
public final class ParseFile {
    private final File file;
    private final ISaveContent saveContent;

    public ParseFile(String file, ISaveContent saveContent) {
        this.file = new File(file);
        this.saveContent = saveContent;
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
     * @param check predicate, that checking an input data on UNICODE
     * @return String
     */
    private String getContentFromFile(Predicate<Integer> check) {
        String output = "";
        try (BufferedInputStream bfr = new BufferedInputStream(new FileInputStream(file), 200)) {
            int data;
            while ((data = bfr.read()) > 0) {
                if (check.test(data)) {
                    output += (char) data;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
}
