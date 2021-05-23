package ru.job4j.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Predicate;

public class ReadFile {
    private final File file;

    public ReadFile(File file) {
        this.file = file;
    }

    private String content(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        File buffer = new File(file.getAbsolutePath());
        try (InputStream i = new FileInputStream(buffer)) {
            int data;
            while ((data = i.read()) > 0) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public String getContent() {
        return content(character -> true);
    }

    public String getContentWithoutUnicode() {
        return content(character -> character < 0x80);
    }
}
