package com.github.tonybaines.java;

import java.util.ArrayList;
import java.util.List;

public class Loops {
    static List<String> rainbow = new ArrayList<>() {{
        add("red");
        add("orange");
        add("yellow");
        add("green");
        add("blue");
        add("indigo");
        add("violet");
    }};


    public static void main(String [] args) {
        for (String colour : rainbow) {
            System.out.println(colour);
        }
    }
}
