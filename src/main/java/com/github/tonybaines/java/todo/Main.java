package com.github.tonybaines.java.todo;

import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        final ToDo toDo = new ToDo();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> toDo.exit()));

        while(true) {
            final PrintWriter writer = System.console().writer();
            writer.println(toDo.read(System.console().readLine("")));
        }
    }
}
