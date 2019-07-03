package com.github.tonybaines.java.todo;

import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ToDoList {
    private static AtomicInteger NEXT_ID = new AtomicInteger(0);
    private Set<ToDoItem> items = new TreeSet<>();

    public ToDoItem add(String description) {
        final ToDoItem item = new ToDoItem(Id.nextId(), description);
        items.add(item);
        return item;
    }

    void verifyExists(Id id) throws NoSuchItemException {
        if (items.stream().noneMatch(item1 -> item1.id.equals(id))) {
            throw new NoSuchItemException();
        }
    }

    public class NoSuchItemException extends Exception {
    }

    public static class Id implements Comparable<Id> {
        private final String id;

        private Id(Integer id) {
            this.id = id.toString();
        }

        Id(String id) {
            this.id = id;
        }

        public static Id nextId() {
            return new Id(NEXT_ID.incrementAndGet());
        }

        public static Id from(String id) {
            return new Id(id);
        }

        @Override
        public int compareTo(@NotNull Id o) {
            return id.compareTo(o.id);
        }

        @Override
        public String toString() {
            return id;
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return (obj instanceof Id) && id.equals(((Id)obj).id);
        }
    }

    public void delete(Id id) throws NoSuchItemException {
        if (items.stream().anyMatch(item1 -> item1.id.equals(id))) {
            items = items.stream().filter(item -> !item.id.equals(id)).collect(Collectors.toSet());
        } else throw new NoSuchItemException();
    }

    void markComplete(Id id) throws NoSuchItemException {
        updateItem(id, (item) -> item.copy().withCompletionStatus(true).done());
    }

    void reword(Id id, String newDesc) throws NoSuchItemException {
        updateItem(id, (item) -> item.copy().withDescription(newDesc).done());
    }

    private void updateItem(Id id, Function<ToDoItem, ToDoItem> update) throws NoSuchItemException {
        if (items.stream().anyMatch(item1 -> item1.id.equals(id))) {
            items = items.stream()
                    .map(item -> {
                        if (item.id.equals(id)) {
                            return update.apply(item);
                        } else {
                            return item;
                        }
                    })
                    .collect(Collectors.toSet());
        } else throw new NoSuchItemException();
    }

    public String prettyPrint() {
        return items.stream().map(Helpers::prettyPrint).collect(Collectors.joining("\n"));
    }

    public static class ToDoItem implements Comparable<ToDoItem> {
        private final Id id;
        private String description;
        private Boolean completed;

        public ToDoItem(Id id, String description) {
            this(id, description, false);
        }

        public ToDoItem(Id id, String description, Boolean completed) {
            this.id = id;
            this.description = description;
            this.completed = completed;
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof ToDoItem) {
                return id.equals(((ToDoItem) obj).id);
            }
            return false;
        }

        @Override
        public int compareTo(@NotNull ToDoItem o) {
            return id.compareTo(o.id);
        }

        public Id getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        Boolean isCompleted() {
            return completed;
        }

        public ToDoItem.Modifier copy() {
            return new Modifier(this);
        }

        public static class Modifier {
            private final ToDoItem item;
            private String newDesc;
            private Boolean newCompletion;

            Modifier(ToDoItem item) {
                this.item = item;
                this.newDesc = item.description;
                this.newCompletion = item.completed;
            }

            public Modifier withDescription(String description) {
                newDesc = description;
                return this;
            }

            public Modifier withCompletionStatus(Boolean completionStatus) {
                newCompletion = completionStatus;
                return this;
            }

            public ToDoItem done() {
                return new ToDoItem(item.id, newDesc, newCompletion);
            }
        }
    }
}
