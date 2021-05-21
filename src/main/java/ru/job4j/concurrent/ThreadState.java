package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) throws InterruptedException {
        Thread main = Thread.currentThread();
        Thread.sleep(5000);

        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );

        first.start();
        second.start();

        int whileIsNotEmpty = 0;

        while (first.getState() != Thread.State.TERMINATED) {
            whileIsNotEmpty++;
        }

        while (second.getState() != Thread.State.TERMINATED) {
            whileIsNotEmpty++;
        }

        System.out.println("Treads are terminated");
    }
}