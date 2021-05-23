package ru.job4j.userstorage;

import org.junit.Test;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserStorageTest {

    private final User user1 = new User(1, 100);
    private final User user2 = new User(2, 200);
    private final User user3 = new User(3, 250);
    private final User user4 = new User(3, 300);

    @Test
    public void whenAdd() throws InterruptedException {
        final UserStorage storage = new UserStorage();
        Thread first = new Thread(
                () -> storage.add(user1)
        );
        Thread second = new Thread(
                () -> storage.add(user1)
        );
        first.start();
        second.start();
        first.join();
        second.join();
        assertEquals(List.of(user1), storage.getUsers());
    }

    @Test
    public void whenAddAndUpdate() throws InterruptedException {
        final UserStorage storage = new UserStorage();
        storage.add(user3);
        Thread first = new Thread(
                () -> {
                    storage.add(user3);
                    storage.update(user4);
                }
        );
        Thread second = new Thread(
                () -> {
                    storage.add(user3);
                    storage.update(user4);
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
        assertEquals(List.of(user4), storage.getUsers());
    }

    @Test
    public void whenAddAndDelete() throws InterruptedException {
        final UserStorage storage = new UserStorage();
        Thread first = new Thread(
                () -> {
                    storage.add(user1);
                    storage.delete(user1);
                }
        );
        Thread second = new Thread(
                () -> {
                    storage.add(user1);
                    storage.delete(user1);
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
        assertEquals(List.of(), storage.getUsers());
    }

    @Test
    public void whenAddAndTransfer() throws InterruptedException {
        final UserStorage storage = new UserStorage();
        Thread first = new Thread(
                () -> {
                    storage.add(user1);
                    storage.add(user2);
                    storage.transfer(2, 1, 100);
                }
        );
        Thread second = new Thread(
                () -> {
                    storage.add(user1);
                    storage.add(user2);
                    storage.transfer(2, 1, 100);
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
        assertEquals(List.of(new User(1, 300), new User(2,0)), storage.getUsers());
    }
}