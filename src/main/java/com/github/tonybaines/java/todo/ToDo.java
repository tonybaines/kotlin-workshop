package com.github.tonybaines.java.todo;

public class ToDo {
    private ToDoList thingsToDo = new ToDoList();
    private Modes mode = Modes.Waiting;

    public static class Prompts {
        public static String ADD_PROMPT = "Please describe the item";
    }

    private enum Modes {Waiting, Adding}

    private static final String CMD_LIST = "list";
    private static final String CMD_ADD = "add";

    public void exit() {
        throw new UnsupportedOperationException();
    }

    public String read(String input) {
        if (mode == Modes.Adding) {
            return handleAdding(input);
        } else {
            if (input.equalsIgnoreCase(CMD_LIST)) {
                return "";
            }
            if (input.equalsIgnoreCase(CMD_ADD)) {
                mode = Modes.Adding;
                return Prompts.ADD_PROMPT;
            }
        }
        throw new UnsupportedOperationException();
    }

    private String handleAdding(String input) {
        thingsToDo.add(input);
        mode = Modes.Waiting;
        return thingsToDo.prettyPrint();
    }
}
