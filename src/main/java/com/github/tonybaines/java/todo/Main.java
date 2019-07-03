package com.github.tonybaines.java.todo;

import java.io.PrintStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final ToDo toDo = new ToDo();

        final PrintStream writer = System.out;
        final Scanner lineReader = new Scanner(System.in);

        writer.println("Welcome to 2 Do.0");
        writer.println("Type 'help' for a list of commands");
        while(true) {
            final String response = toDo.read(lineReader.next().trim());
            if (response.equalsIgnoreCase(ToDo.Commands.EXIT.name())) break;
            writer.println(response);
        }
    }
}
