package ru.job4j.queue;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.junit.Assert.assertEquals;

public class SimpleBlockingQueueTest {
    @Test
    public void twoThreads() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(
                () -> {
                    queue.offer(1);
                    queue.offer(2);
                    queue.offer(3);
                    queue.offer(4);
                    queue.offer(5);
                }
        );
        Thread consumer = new Thread(
                () -> {
                    queue.poll();
                    queue.poll();
                    queue.poll();
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