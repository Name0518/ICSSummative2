package icssummative2;

import processing.core.PApplet;
import processing.core.PImage;

public class Enemy {
    private int x, y;
    private int width, height;
    private int health;
    private int damage;
    private float attackSpeed; // attacks per second
    private int attackRange;
    private long lastAttackTime;
    private PImage sprite;
    public static int enemyCount;

    public Enemy(int x, int y, int width, int height, int health, int damage, float attackSpeed, int attackRange, String spritePath, PApplet app) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.health = health;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.attackRange = attackRange;
        this.lastAttackTime = 0;
        this.sprite = app.loadImage(spritePath);
        enemyCount++;
    }

    public void display(PApplet app) {
        app.image(sprite, x, y, width, height);
        app.fill(255, 0, 0);
        app.rect(x, y - 10, width * (health / 100.0f), 5); // health bar
    }

    public void moveToward(int targetX, int targetY) {
        if (targetX < x) x--;
        if (targetX > x) x++;
        if (targetY < y) y--;
        if (targetY > y) y++;
    }

    public boolean canAttack(long currentTime) {
        return currentTime - lastAttackTime >= (1000 / attackSpeed);
    }

    public boolean inRange(int targetX, int targetY) {
        int dx = targetX - x;
        int dy = targetY - y;
        return dx * dx + dy * dy <= attackRange * attackRange;
    }

    public void attack(PlayableCharacter pc, long currentTime) {
        if (canAttack(currentTime) && inRange(pc.getX(), pc.getY())) {
            pc.takeDamage(damage);
            lastAttackTime = currentTime;
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void takeDamage(int dmg) {
        health -= dmg;
        if (health < 0) health = 0;
    }

    public int getX() { 
        return x; 
    }
    public int getY() { 
        return y; 
    }

}
