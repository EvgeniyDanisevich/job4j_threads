package ru.job4j.queue;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.junit.Assert.assertEquals;

public class SimpleBlockingQueueTest {
    @Test
    public void twoThreads() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
                () -> {
                    try {
                        queue.offer(1);
                        queue.offer(2);
                        queue.offer(3);
                        queue.offer(4);
                        queue.offer(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    try {
                        queue.poll();
                        queue.poll();
                        queue.poll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        Queue<Integer> expected = new LinkedList<>(List.of(4, 5));
        assertEquals(expected, List.of(queue.poll(), queue.poll()));
    }
}