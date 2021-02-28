import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Provides functions to handle user input
 */
public class UserInput {

    /**
     * Get an integer input from a user.
     * Checks is min <= i <= max
     *
     * @param max int, Max value
     * @param min int, Min value
     * @return int, user inputted integer
     */
    public static int getIntInput(int min, int max) {
        Scanner in = new Scanner(System.in);
        boolean isInt = false;
        boolean inRange = false;
        int i = 0;

        while (!inRange) {
            while (!isInt) {
                try {
                    System.out.print(">>> ");
                    i = in.nextInt();
                    in.nextLine();
                    isInt = true;
                } catch (InputMismatchException e) {
                    System.out.println("Please enter an integer!");
                    in.nextLine();
                }
            }
            if (i >= min && i <= max) {
                inRange = true;
            } else {
                System.out.println("Please enter an integer from " + min + " - " + max + " inclusive!");
                isInt = false;
            }
        }

        return i;
    }

    /**
     * Get an integer input from a user.
     *
     * @return int, user inputted int
     */
    public static int getIntInput() {
        Scanner in = new Scanner(System.in);
        boolean isInt = false;
        int i = 0;
        while (!isInt) {
            try {
                System.out.print(">>> ");
                i = in.nextInt();
                in.nextLine();
                isInt = true;
            } catch (InputMismatchException e) {
                System.out.println("Please enter an integer!");
                in.nextLine();
            }
        }
        return i;
    }

    /**
     * Get user to chose from an array of strings
     *
     * @param strings String[], array containing valid user inputs
     * @return String, returns user inputted String
     */
    public static String getStringInput(String[] strings) {
        Scanner in = new Scanner(System.in);
        boolean exit = false; //This will be set to true when numeric val entered
        String input = " ";

        while (!exit) {
            System.out.print(">>> ");
            input = in.nextLine();
            for (String s : strings) {
                if (s.equals(input)) {
                    exit = true;
                    break;
                }
            }
            if (!exit) {
                System.out.println("Please enter a valid input!");
                System.out.println("Valid Inputs are:");
                printArray(strings);
            }
        }
        return input;
    }

    /**
     * Prints an array with each element on its own line
     *
     * @param strings String[], strings to be printed
     */
    public static void printArray(String[] strings) {
        for (String string : strings) {
            System.out.println(string);
        }
    }

    /**
     * Prints an array with each element on its own line
     *
     * @param ints int[], ints to be printed
     */
    public static void printArray(int[] ints) {
        for (int anInt : ints) {
            System.out.println(anInt);
        }
    }

    /**
     * Prints an array with each element on its own line
     *
     * @param sv Integer[], StoreView array to be printed
     */
    public static void printArray(ArrayList<StoreView> sv) {
        for (StoreView storeView : sv) {
            System.out.println(storeView);
        }
    }
}
