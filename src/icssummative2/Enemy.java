package icssummative2;

import processing.core.PApplet;
import processing.core.PImage;

public class Enemy {
    private int x, y;
    private int width, height;
    private int health;
    private int damage;
    private double attackSpeed; // attacks per second
    private int attackRange;
    private double lastAttackTime;
    private PImage sprite;
    public static int enemyCount;
    
    /**
     * Constructs an enemy with specific attributes and loads its sprite image.
     *
     * @param x            X-coordinate of the enemy
     * @param y            Y-coordinate of the enemy
     * @param width        Width of the enemy sprite
     * @param height       Height of the enemy sprite
     * @param health       Starting health of the enemy
     * @param damage       Amount of damage dealt to the player per attack
     * @param attackSpeed  Attacks per second
     * @param attackRange  How close the player must be for the enemy to attack
     * @param spritePath   Path to the enemy's image file
     * @param app          PApplet used to load the image
     */
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
        enemyCount++; // Increase count when enemy is created
    }

     /**
     * Displays the enemy sprite and a red health bar above it.
     *
     * @param app PApplet used to draw on the screen
     */
    public void display(PApplet app) {
        app.image(sprite, x, y, width, height);
        app.fill(255, 0, 0);
        app.rect(x, y - 10, width * (health / 100.0f), 5); // health bar
    }

    /**
     * Moves the enemy one pixel toward the target coordinates.
     *
     * @param targetX X-coordinate of the target (e.g., player)
     * @param targetY Y-coordinate of the target
     */
    public void moveToward(int targetX, int targetY) {
        if (targetX < x) 
            x--;
        if (targetX > x) 
            x++;
        if (targetY < y) 
            y--;
        if (targetY > y) 
            y++;
    }

    /**
     * Checks whether the enemy can attack based on its attack speed and cooldown
     *
     * @param currentTime Current time in milliseconds
     * @return true if the enemy is ready to attack
     */
    public boolean canAttack(long currentTime) {
        return (currentTime - lastAttackTime) >= (1000 / attackSpeed);
    }

     /**
     * Determines if target is within attack range
     *
     * @param targetX Target's X-coordinate
     * @param targetY Target's Y-coordinate
     * @return true if within circular attack range
     */
    public boolean inRange(int targetX, int targetY) {
        int dx = targetX - x;
        int dy = targetY - y;
        return dx * dx + dy * dy <= attackRange * attackRange;
    }

     /**
     * Attacks player if the player is in range and cooldown has passed
     *
     * @param pc          The player's character
     * @param currentTime Current time in milliseconds
     */
    public void attack(PlayableCharacter pc, long currentTime) {
        if (canAttack(currentTime) && inRange(pc.getX(), pc.getY())) {
            pc.takeDamage(damage);
            lastAttackTime = currentTime;
        }
    }

    /**
     * Checks whether the enemy is still alive.
     *
     * @return true if health is above zero
     */
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * Reduces enemy health by the given amount
     *
     * @param dmg Amount of damage taken
     */
    public void takeDamage(int dmg) {
        health -= dmg;
        if (health < 0) 
            health = 0;
    }

    
    /** 
     * @return X-coordinate
     */
    public int getX() { 
        return x; 
    }
    
    /** 
     * @return Y-coordinate
     */
    public int getY() { 
        return y; 
    }
}
