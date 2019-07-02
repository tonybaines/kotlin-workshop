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
        final ToDoItem item = new ToDoItem(nextId(), description);
        items.add(item);
        return item;
    }

    public void verifyExists(String id) throws NoSuchItemException {
        if (items.stream().noneMatch(item1 -> item1.id.equals(id))) {
            throw new NoSuchItemException();
        }
    }

    public class NoSuchItemException extends Exception {
    }

    private static Integer nextId() {
        return NEXT_ID.incrementAndGet();
    }

    public void delete(String id) throws NoSuchItemException {
        if (items.stream().anyMatch(item1 -> item1.id.equals(id))) {
            items = items.stream().filter(item -> !item.id.equals(id)).collect(Collectors.toSet());
        } else throw new NoSuchItemException();
    }

    public void markComplete(String id) throws NoSuchItemException {
        updateItem(id, (item) -> item.copy().withCompletionStatus(true).done());
    }

    public void reword(String id, String newDesc) throws NoSuchItemException {
        updateItem(id, (item) -> item.copy().withDescription(newDesc).done());
    }

    private void updateItem(String id, Function<ToDoItem, ToDoItem> update) throws NoSuchItemException {
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
        private final String id;
        private String description;
        private Boolean completed;

        public ToDoItem(Integer id, String description) {
            this(id.toString(), description, false);
        }

        public ToDoItem(String id, String description, Boolean completed) {
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

        public String getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        public Boolean isCompleted() {
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
