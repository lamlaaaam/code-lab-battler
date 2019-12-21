import java.util.Scanner;

public class Game {
    private static Player getPlayer() {
        Scanner in =  new Scanner(System.in);
        System.out.println("Welcome to a shitty game based on codelab members.");
        String playerName = Inputs.getName();
        System.out.printf("Hello, %s! Good to see you!%n", playerName);
        Player player = new Player(playerName);
        return player;
    }
    public static void main(String[] args) {
        Clear.clear();
        Player player = getPlayer();
        while (true) {
            int choice = Inputs.getChoice();
            Clear.clear();
            if (choice == 1) {
                player.showInfo();
            } else if (choice == 2) {
                int monsterMaxLevel = player.getLevel() + 1;
                int monsterMinLevel = player.getLevel() <= 3 ? 1 : player.getLevel() - 3;
                Monster monster = MonsterSpawner.spawn(monsterMinLevel, monsterMaxLevel);
                Battler.battle(monster, player);
            } else if (choice == 3) {
                System.out.println("You are back to full health!");
                player.heal(9999999); // LMAOO
            } else {
                System.out.println("Nooooo ok bb");
                System.exit(0);
            }
        }

    }
}
