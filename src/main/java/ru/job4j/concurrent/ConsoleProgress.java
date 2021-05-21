package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        char[] process = new char[]{'\\', '|', '/'};
        while (!Thread.currentThread().isInterrupted()) {
            for (char c : process) {
                try {
                    System.out.print("\r load: " + c);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(1500);
        progress.interrupt();
    }
}
