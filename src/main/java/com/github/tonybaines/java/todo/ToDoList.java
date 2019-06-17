package com.github.tonybaines.java.todo;

import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ToDoList {
    private static AtomicInteger NEXT_ID = new AtomicInteger(0);
    private Set<ToDoItem> items = new TreeSet<ToDoItem>();

    public void add(String description) {
        items.add(new ToDoItem(nextId(), description));
    }

    private static Integer nextId() {
        return NEXT_ID.incrementAndGet();
    }

    public String prettyPrint() {
        return items.stream().map(item -> item.prettyPrint()).collect(Collectors.joining("\n"));
    }

    private class ToDoItem implements Comparable<ToDoItem> {
        private final Integer id;
        private final String desciption;

        private ToDoItem(Integer id, String desciption) {
            this.id = id;
            this.desciption = desciption;
        }

        public String prettyPrint() {
            return String.format("[%d] %s", id, desciption);
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof ToDoItem) {
                return id.equals(((ToDoItem)obj).id);
            }
            return false;
        }

        @Override
        public int compareTo(@NotNull ToDoItem o) {
            return id.compareTo(o.id);
        }
    }
}
