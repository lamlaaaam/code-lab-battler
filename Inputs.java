import java.util.Scanner;

public class Inputs {
    public static String getName() {
        Scanner nameInput = new Scanner(System.in);
        System.out.println("What's your name?");
        String name = nameInput.nextLine();
        return name;
    }
    public static int getChoice() {
        Scanner in = new Scanner(System.in);
        System.out.printf("[1] See your stats%n[2] Spawn a monster!%n[3] Heal up!%n[4] Quit game (but y would u lmao)%n");
        if (in.hasNextInt()) {
            int choice = in.nextInt();
            if (choice < 0 || choice > 4) {
                System.out.println("Invalid choice!");
                return getChoice();
            } else {
                return choice;
            }
        } else {
            System.out.println("Invalid choice!");
            return getChoice();
        }

    }
}
