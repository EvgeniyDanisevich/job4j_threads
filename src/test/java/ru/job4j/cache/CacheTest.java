package ru.job4j.cache;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CacheTest {

    private final Base base1 = new Base(1, 0);
    private final Base base2 = new Base(2, 0);
    private final Base base3 = new Base(3, 0);
    private final Base base4 = new Base(4, 0);
    private final Base base4updated = new Base(4, 0);
    private final Base base4exception = new Base(4, 1);

    @Test
    public void whenAdd() {
        Cache cache = new Cache();
        cache.add(base1);
        cache.add(base2);
        assertEquals(List.of(base1, base2), List.of(cache.get(1), cache.get(2)));
    }

    @Test
    public void whenUpdate() {
        Cache cache = new Cache();
        cache.add(base3);
        cache.add(base4);
        boolean isUpdated = cache.update(base4updated);
        assertTrue(isUpdated);
        assertEquals(new Base(4, 1), cache.get(4));
    }

    @Test (expected = OptimisticException.class)
    public void whenUpdateWithException() {
        Cache cache = new Cache();
        cache.add(base4);
        cache.update(base4exception);
    }

    @Test
    public void whenDelete() {
        Cache cache = new Cache();
        cache.add(base1);
        cache.add(base2);
        cache.delete(base1);
        assertEquals(base2, cache.get(2));
    }
}