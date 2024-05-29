package MainFunctionality;

import java.util.Scanner;

/**
 * A class representing a command prompt interface for user interaction.
 * Continuously reads user input from the console and executes corresponding commands.
 */
public class CommandPrompt {
    /**
     * Starts the command prompt interface.
     * Continuously reads user input from the console and executes corresponding commands
     * until the application is terminated.
     */
    public static void start() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String input = sc.nextLine();
            if (input.isEmpty()) {
                continue;
            }
            try {
                CommandFactory.executeCommand(input);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println();
        }
    }
}
