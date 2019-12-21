import java.util.Random;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;
import java.util.Collections;

public class MonsterSpawner {
    private List<String> monsterNames;
    private final int HEALTHSCALE = 10;
    private final int ATTACKSCALE = 3;
    public MonsterSpawner() {
        monsterNames = Collections.emptyList();
        try {
            monsterNames = Files.readAllLines(Paths.get("./monsters/monsternames.txt"));
        } catch (IOException e) {
            System.out.println("Exception");
        }
    }
    public Monster spawn(int minLevel, int maxLevel) {
        Random rand = new Random();
        String name = monsterNames.get(rand.nextInt(monsterNames.size()));
        int level = rand.nextInt(maxLevel - minLevel + 1) + minLevel;
        int health = HEALTHSCALE * level;
        int maxAttack = ATTACKSCALE * level;

        Monster monster = new Monster(name, health, level, maxAttack);
        return monster;
    }
}
