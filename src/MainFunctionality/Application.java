package MainFunctionality;

import java.util.Scanner;

/**
 * Application class to initiate the application.
 * It sets up a command-line interface where users can enter commands to interact with the system.
 */
public class Application {
    /**
     * It continuously reads user input from the command line and executes corresponding commands
     * until the application is exited. Errors during command execution are caught and their messages are displayed.
     *
     * @param args Command-line arguments passed to the program; not used in this implementation.
     */
    public static void main(String[] args) {
        CommandPrompt.start();
    }
}