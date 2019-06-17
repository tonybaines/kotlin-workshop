package com.github.tonybaines.java.todo;

import static com.github.tonybaines.java.todo.Helpers.prettyPrint;

public class ToDo {
    private ToDoList thingsToDo = new ToDoList();
    private Modes mode = Modes.Waiting;
    public static class Prompts {
        public static String ADD = "Please describe the item";
        public static String DELETE = "Please enter the ID number to delete";
    }

    public static class Messages {
        public static final String SUCCESS = "Success";
    }

    private enum Modes {Waiting, Adding, Deleting;}

    private static class Commands {
        static final String LIST = "list";
        static final String ADD = "add";
        static final String DELETE = "delete";
    }

    public void exit() {
        throw new UnsupportedOperationException();
    }

    public String read(String input) {
        if (mode == Modes.Adding) {
            return handleAdding(input);
        } else if (mode == Modes.Deleting) {
            return handleDeleting(input);
        } else {
            if (input.equalsIgnoreCase(Commands.LIST)) {
                return thingsToDo.prettyPrint();
            }
            if (input.equalsIgnoreCase(Commands.ADD)) {
                mode = Modes.Adding;
                return Prompts.ADD;
            }
            if (input.equalsIgnoreCase(Commands.DELETE)) {
                mode = Modes.Deleting;
                return Prompts.DELETE;
            }
        }
        throw new UnsupportedOperationException();
    }

    private String handleDeleting(String id) {
        thingsToDo.delete(id);
        mode = Modes.Waiting;
        return Messages.SUCCESS;
    }

    private String handleAdding(String input) {
        String newItem = prettyPrint(thingsToDo.add(input));
        mode = Modes.Waiting;
        return String.format("%s - %s", Messages.SUCCESS, newItem);
    }
}
