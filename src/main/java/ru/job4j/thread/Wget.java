package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String name;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
        this.name = getName(url);
    }

    private String getName(String url) {
        String[] strings = url.split("/");
        return strings[strings.length - 1];
    }

    @Override
    public void run() {
        long expected = speed * 1_000_000_000L;
        long timeStampStart;
        long timeStampEnd;
        long loadTime;
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("load_" + name)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                timeStampStart = System.nanoTime();
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                timeStampEnd = System.nanoTime();
                loadTime = timeStampEnd - timeStampStart;
                if (expected > loadTime) {
                    try {
                        Thread.sleep((expected - loadTime) / 1_000_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}