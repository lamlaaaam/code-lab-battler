import java.util.Scanner;

public class Battler {
    private static int battleInput() {
        Scanner in = new Scanner(System.in);
        System.out.println("What do you want to do?\n[1] Fight\n[2] Flee");
        if (in.hasNextInt()) {
            int choice = in.nextInt();
            if (choice < 1 || choice > 2) {
                System.out.println("Invalid choice!");
                return battleInput();
            } else {
                return choice;
            }
        } else {
            System.out.println("Invalid choice!");
            return battleInput();
        }
    }
    public static void battle(Monster monster, Player player) {
        Clear.clear();
        System.out.printf("You encountered a %s!%n", monster.getName());
        String turn = "player";
        monster.showInfo();
        player.showInfo();
        while (true) {
            if (player.getHealth() <= 0) {
                System.out.println("You died... Game Over");
                System.exit(0);
            } else if (monster.getHealth() <= 0) {
                System.out.println("You won!");
                int expGain = monster.getExpValue();
                System.out.printf("You gained %d exp!%n", expGain);
                player.gainExp(expGain);
                break;

            } else if (turn == "player") {
                int choice = battleInput();
                Clear.clear();
                if (choice == 1) {
                    int playerAttack = player.rollAttack();
                    System.out.printf("You dealt %d damage to %s!%n", playerAttack, monster.getName());
                    monster.damage(playerAttack);
                    turn = "monster";
                } else {
                    System.out.println("You ran away!");
                    break;
                }
            } else {
                    int monsterAttack = monster.rollAttack();
                    System.out.printf("%s dealt %d damage to you!%n", monster.getName(), monsterAttack);
                    player.damage(monsterAttack);
                    monster.showInfo();
                    player.showInfo();
                    turn = "player";
            }
        }
    }
}
