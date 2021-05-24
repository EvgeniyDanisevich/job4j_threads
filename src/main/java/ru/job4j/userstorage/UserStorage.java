package ru.job4j.userstorage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.*;
import java.util.stream.Collectors;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final Map<Integer, User> users = new HashMap<>();

    public synchronized void add(User user) {
        users.putIfAbsent(user.getId(), user);
    }

    public synchronized void update(User user) {
        users.replace(user.getId(), user);
    }

    public synchronized void delete(User user) {
        users.remove(user.getId(), user);
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        User first = users.get(fromId);
        User second = users.get(toId);
        if (first != null && second != null && first.getAmount() >= amount) {
            first.setAmount(first.getAmount() - amount);
            second.setAmount(second.getAmount() + amount);
        }
    }

    public synchronized List<User> getUsers() {
        return users.values().stream()
                .map(user -> new User(user.getId(), user.getAmount()))
                .collect(Collectors.toList());
    }
}
