package ru.job4j.userstorage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final List<User> users = Collections.synchronizedList(new ArrayList<>());

    private synchronized User containUser(User user) {
        for (User u : users) {
            if (u.getId() == user.getId()) {
                return u;
            }
        }
        return null;
    }

    public synchronized boolean add(User user) {
        if (containUser(user) == null) {
            users.add(user);
            return true;
        }
        return false;
    }

    public synchronized boolean update(User user) {
        User u = containUser(user);
        if (u != null) {
            u.setAmount(user.getAmount());
            return true;
        }
        return false;
    }

    public synchronized boolean delete(User user) {
        User u = containUser(user);
        if (containUser(user) != null) {
            return users.remove(u);

        }
        return false;
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        User first = null;
        User second = null;
        for (User u : users) {
            if (u.getId() == fromId) {
                first = u;
            }
            if (u.getId() == toId) {
                second = u;
            }
            if (first != null && second != null) {
                first.setAmount(first.getAmount() - amount);
                second.setAmount(second.getAmount() + amount);
            }
        }
    }

    public synchronized List<User> getUsers() {
        return users.stream()
                .map(user -> new User(user.getId(), user.getAmount()))
                .collect(Collectors.toList());
    }
}
