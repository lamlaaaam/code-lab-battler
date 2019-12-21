import java.util.Random;

public class Player {
    private String name;
    private int health = 10;
    private int level = 1;
    private int maxHealth = 10;
    private int exp = 0;
    private int levelUpExp = 5;
    private final int ATTACKSCALE = 4;
    public Player(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public int getHealth() {
        return health;
    }
    public int getMaxHealth() {
        return maxHealth;
    }
    public int getLevel() {
        return level;
    }
    public int getExp() {
        return exp;
    }
    public int getMaxExp() {
        return levelUpExp;
    }
    public void showInfo() {
        System.out.println("**********************");
        System.out.printf("%s%nHealth: %d/%d%nLevel: %d%nExp: %d/%d%n", name, health, maxHealth, level, exp, levelUpExp);
        System.out.println("**********************");
    }
    public void damage(int damageAmount) {
        health -= damageAmount;
        if (health < 0) {
            health = 0;
        }
    }
    public void heal(int healAmount) {
        health += healAmount;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }
    public void gainExp(int expAmount) {
        exp += expAmount;
        if (exp >= levelUpExp) {
            levelUp();
        }
    }
    private void levelUp() {
        level += 1;
        System.out.printf("Level up!%nYou are now level %d!%n", level);
        exp = 0;
        maxHealth = level * 10;
        levelUpExp = level * 5;
        health = maxHealth;
    }
    public int rollAttack() {
        Random rand = new Random();
        int attack = rand.nextInt(level * ATTACKSCALE) + 1;
        return attack;
    }
}
