package de.wuespace.telestion.terminal_client;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");

        String input;
        boolean loop = true;
        Scanner scanner = new Scanner(System.in);

        while (loop) {
            System.out.print("> ");
            input = scanner.nextLine();
            switch (input) {
                case "/exit", "/quit" -> loop = false;
                default -> System.out.println(input);
            }
        }

        System.out.println("Quit");
    }
}
