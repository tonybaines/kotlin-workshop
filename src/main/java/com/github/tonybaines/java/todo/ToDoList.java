package com.github.tonybaines.java.todo;

import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ToDoList {
    private static AtomicInteger NEXT_ID = new AtomicInteger(0);
    private Set<ToDoItem> items = new TreeSet<>();

    public ToDoItem add(String description) {
        final ToDoItem item = new ToDoItem(nextId(), description);
        items.add(item);
        return item;
    }

    private static Integer nextId() {
        return NEXT_ID.incrementAndGet();
    }

    public void delete(String id) {
        if (items.stream().anyMatch(item1 -> item1.id.equals(id))) {
            items = items.stream().filter(item -> item.id.equals(id)).collect(Collectors.toSet());
        } else throw new IllegalArgumentException("No item found with id " + id);
    }

    public String prettyPrint() {
        return items.stream().map(Helpers::prettyPrint).collect(Collectors.joining("\n"));
    }

    public class ToDoItem implements Comparable<ToDoItem> {
        private final String id;
        private final String description;

        private ToDoItem(Integer id, String description) {
            this.id = id.toString();
            this.description = description;
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

        public String getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }
    }
}
