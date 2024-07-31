package javamonkey;

import java.util.Scanner;

import javamonkey.repl.Repl;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the Monkey programming language!");
        Repl.start(input);
    }
}