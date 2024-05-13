import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Basic infinite loop mimicking a cmd
        while (true){
            System.out.print("> ");
            String input = sc.nextLine();
            if (input.isEmpty()) {
                continue;
            }
            try {
                CommandFactory.executeCommand(input);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
            System.out.println();
        }
    }
}