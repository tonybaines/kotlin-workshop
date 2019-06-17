package com.github.tonybaines.java.todo;

public class Helpers {
    public static String prettyPrint(ToDoList.ToDoItem item) {
        return String.format("[%s] %s", item.getId(), item.getDescription());
    }
}
