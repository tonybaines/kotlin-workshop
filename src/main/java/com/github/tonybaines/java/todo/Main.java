package com.github.tonybaines.java.todo;

import java.io.PrintWriter;

/**
 * Will not work from an IDE!
 * Build the JAR
 *  $ ./gradlew jar
 * run the application
 *  $ java -jar build/libs/kotlin-workshop.jar
 */
public class Main {
    public static void main(String[] args) {
        final ToDo toDo = new ToDo();

        while(true) {
            final PrintWriter writer = System.console().writer();
            final String response = toDo.read(System.console().readLine(""));

            if (response.equalsIgnoreCase(ToDo.Commands.EXIT.name())) break;
            writer.println(response);
        }
    }
}
