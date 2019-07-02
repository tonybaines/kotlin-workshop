package com.github.tonybaines.java.todo;

public class Helpers {
    public static String prettyPrint(ToDoList.ToDoItem item) {
        final String pretty = String.format("[%s] %s", item.getId(), item.getDescription());
        if (item.isCompleted()) {
            return pretty + " (DONE)";
        } else {
            return pretty;
        }
    }
}
