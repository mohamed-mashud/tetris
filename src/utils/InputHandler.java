package utils;

import java.util.Scanner;

public class InputHandler {
    public static final Scanner scanner = new Scanner(System.in);

    public static String getStringInput() {
        return scanner.nextLine();
    }

    public static int getIntegerInput() {
        int value;
        while (true) {
            try {
                value =  Integer.parseInt(getStringInput());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Enter valid number : ");
            }
        }
        return value;
    }
}
