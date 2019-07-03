package com.github.tonybaines.java.todo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.tonybaines.java.todo.Helpers.prettyPrint;

public class ToDo {
    private ToDoList thingsToDo = new ToDoList();
    private Modes mode = new Modes.Waiting();

    public static class Prompts {
        public static String ADD = "Please describe the item";
        public static String DELETE = "Please enter the ID number to delete";
        public static String COMPLETE = "Please enter the ID number to complete";
        public static String REWORD = "Please enter the ID number to reword";
        public static String REWORD_TEXT = "Please enter the new description";
    }

    public static class Messages {
        public static final String SUCCESS = "Success";
        public static final String FAILURE = "Failed";
    }

    /* Can't use an enum here because one state needs
     * to hold some context
     */
    private static abstract class Modes {
        static class Waiting extends Modes {
        }

        static class Adding extends Modes {
        }

        static class Deleting extends Modes {
        }

        static class Completing extends Modes {
        }

        static class RewordingSelect extends Modes {
        }

        static class RewordingText extends Modes {
            final String id;

            RewordingText(String id) {
                this.id = id;
            }
        }
    }

    enum Commands {
        LIST, ADD, DELETE, COMPLETE, REWORD, HELP, EXIT;

        public static List list() {
            return Stream.of(values()).map(c -> c.name().toLowerCase()).collect(Collectors.toList());
        }
    }

    public String exit() {
        return Commands.EXIT.toString();
    }

    public String read(String input) {
        if (mode instanceof Modes.Adding) {
            return handleAdding(input);
        } else if (mode instanceof Modes.Deleting) {
            return handleDeleting(input);
        } else if (mode instanceof Modes.Completing) {
            return handleCompleting(input);
        } else if (mode instanceof Modes.RewordingSelect) {
            return handleRewordSelection(input);
        } else if (mode instanceof Modes.RewordingText) {
            return handleRewordText(input);
        } else {
            if (input.equalsIgnoreCase(Commands.LIST.name())) {
                return thingsToDo.prettyPrint();
            }
            if (input.equalsIgnoreCase(Commands.ADD.name())) {
                mode = new Modes.Adding();
                return Prompts.ADD;
            }
            if (input.equalsIgnoreCase(Commands.DELETE.name())) {
                mode = new Modes.Deleting();
                return Prompts.DELETE;
            }
            if (input.equalsIgnoreCase(Commands.COMPLETE.name())) {
                mode = new Modes.Completing();
                return Prompts.COMPLETE;
            }
            if (input.equalsIgnoreCase(Commands.REWORD.name())) {
                mode = new Modes.RewordingSelect();
                return Prompts.REWORD;
            }
            if (input.equalsIgnoreCase(Commands.HELP.name())) {
                return Commands.list().stream().collect(Collectors.joining("\n")).toString();
            }
            if (input.equalsIgnoreCase(Commands.EXIT.name())) {
                return exit();
            }
        }
        throw new UnsupportedOperationException();
    }

    private String handleAdding(String input) {
        if (input.trim().isEmpty()) {
            return String.format("%s - the description cannot be blank", Messages.FAILURE);
        } else {
            String newItem = prettyPrint(thingsToDo.add(input));
            mode = new Modes.Waiting();
            return String.format("%s - %s", Messages.SUCCESS, newItem);
        }
    }

    private String handleDeleting(String id) {
        return handlingBadId(id, it -> {
            thingsToDo.delete(ToDoList.Id.from(it));
            mode = new Modes.Waiting();
            return Messages.SUCCESS;
        });
    }

    private String handleCompleting(String id) {
        return handlingBadId(id, it -> {
            thingsToDo.markComplete(ToDoList.Id.from(id));
            mode = new Modes.Waiting();
            return Messages.SUCCESS;
        });
    }

    private String handleRewordText(String newDesc) {
        final String id = ((Modes.RewordingText) mode).id;
        return handlingBadId(id, it -> {
            thingsToDo.reword(ToDoList.Id.from(it), newDesc);
            mode = new Modes.Waiting();
            return Messages.SUCCESS;
        });
    }

    private String handleRewordSelection(String id) {
        return handlingBadId(id, it -> {
            thingsToDo.verifyExists(ToDoList.Id.from(it));
            mode = new Modes.RewordingText(id);
            return Prompts.REWORD_TEXT;
        });
    }

    // Needs a new interface because the java.util.function interfaces don't allow 'throws'
    @FunctionalInterface
    interface ThrowingFunction {
        String apply(String id) throws ToDoList.NoSuchItemException;

    }

    private String handlingBadId(String id, ThrowingFunction op) {
        try {
            return op.apply(id);
        } catch (ToDoList.NoSuchItemException e) {
            return String.format("%s: No item with ID '%s'", Messages.FAILURE, id);
        }
    }
}
