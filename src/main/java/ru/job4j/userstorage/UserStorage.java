package ru.job4j.userstorage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.*;
import java.util.stream.Collectors;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final Map<Integer, User> users = new HashMap<>();

    public synchronized boolean add(User user) {
        if (!users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            return true;
        }
        return false;
    }

    public synchronized boolean update(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            return true;
        }
        return false;
    }

    public synchronized boolean delete(User user) {
        return users.remove(user.getId(), user);
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        User first;
        User second;
        if (users.containsKey(fromId) && users.containsKey(toId)) {
            first = users.get(fromId);
            second = users.get(toId);
            if (first.getAmount() >= amount) {
                first.setAmount(first.getAmount() - amount);
                second.setAmount(second.getAmount() + amount);
            }
        }
    }

    public synchronized List<User> getUsers() {
        return users.values().stream()
                .map(user -> new User(user.getId(), user.getAmount()))
                .collect(Collectors.toList());
    }
}
