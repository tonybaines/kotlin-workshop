package com.github.tonybaines.java.todo;

public class ToDo {

    private static final String CMD_LIST = "list";

    public void exit() {
        throw new UnsupportedOperationException();
    }

    public String read(String input) {
        if (input.equalsIgnoreCase(CMD_LIST)) {
            return "";
        }
        throw new UnsupportedOperationException();
    }
}
