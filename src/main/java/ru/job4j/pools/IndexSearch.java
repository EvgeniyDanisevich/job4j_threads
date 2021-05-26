package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class IndexSearch<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final T object;
    private final int from;
    private final int to;

    public IndexSearch(T[] array, T object, int from, int to) {
        this.array = array;
        this.object = object;
        this.from = from;
        this.to = to;
    }

    public IndexSearch(T[] array, T object) {
        this.array = array;
        this.object = object;
        this.from = 0;
        this.to = array.length - 1;
    }

    @Override
    protected Integer compute() {
        if (to - from + 1 <= 10) {
            return linearSearch(from, to);
        }
        int mid = (from + to) / 2;
        IndexSearch<T> leftSearch = new IndexSearch<>(array, object, from, mid);
        IndexSearch<T> rightSearch = new IndexSearch<>(array, object, mid + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        Integer left = leftSearch.join();
        Integer right = rightSearch.join();
        return left == -1 ? right : left;
    }

    private Integer linearSearch(int from, int to) {
        for (int i = from; i <= to; i++) {
            if (array[i].equals(object)) {
                return i;
            }
        }
        return -1;
    }

    public Integer find(T object) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new IndexSearch<>(array, object, from, to));
    }
}
