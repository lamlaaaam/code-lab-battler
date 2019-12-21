import java.util.Random;

public class Monster {
    private String name;
    private int health;
    private int maxHealth;
    private int level;
    private int maxAttack;
    private final int EXPSCALE = 3;
    public Monster(String name, int health, int level, int maxAttack) {
        this.name = name;
        this.health = health;
        maxHealth = health;
        this.level = level;
        this.maxAttack = maxAttack;
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
    public void showInfo() {
        System.out.println("**********************");
        System.out.printf("%s%nHealth: %d/%d%nLevel: %d%n", name, health, maxHealth, level);
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
    }
    public int rollAttack() {
        Random rand = new Random();
        int attack = rand.nextInt(maxAttack) + 1;
        return attack;
    }
    public int getExpValue() {
        return level * EXPSCALE;
    }
}
