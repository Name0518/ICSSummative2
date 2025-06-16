package icssummative2;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

public class PlayableCharacter {
    private int x, y;
    private int speed;
    private int health;
    private int width, height;
    private PApplet app;
    private PImage sprite;

    /**
     * Constructs a new playable character.
     * @param x Starting x-position
     * @param y Starting y-position
     * @param width Width of the character sprite
     * @param height Height of the character sprite
     * @param spritePath Path to the character image file
     * @param app PApplet instance for image loading and rendering
     */
    public PlayableCharacter(int x, int y, int width, int height, String spritePath, PApplet app) {
        this.x = x;
        this.y = y;
        this.speed = 5;
        this.health = 100;
        this.width = width;
        this.height = height;
        this.app = app;
        this.sprite = app.loadImage(spritePath);
    }
    
    /**
     * Draws the character and their health bar on screen.
     * @param app PApplet instance for rendering
     */
    public void display(PApplet app) {
        app.image(sprite, x, y, width, height);
        app.fill(0, 255, 0);
        app.rect(x, y - 10, width * (health / 100.0f), 5); // health bar
    }

    /**
     * Moves the character based on direction input.
     * @param dx Change in x-direction 
     * @param dy Change in y-direction 
     */
    public void move(int dx, int dy) {
        x += dx * speed;
        y += dy * speed;
    }

    /**
     * Reduces the character's health by a given amount.
     * @param dmg Amount of damage to take
     */
    public void takeDamage(int dmg) {
        health -= dmg;
        if (health < 0) {
            health = 0;
        }
    }

    /**
     * Checks whether the character is still alive.
     * @return true if health > 0, false otherwise
     */
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * @return Current x-position
     */
    public int getX() { 
        return x; 
    }
    
    /**
     * @return Current y-position
     */
    public int getY() { 
        return y; 
    }
    
    /**
     * @return Current health
     */
    public int getHealth(){
        return health;
    }
    
    /**
     * Sets the current sprite image.
     * @param sprite New PImage to use for the character
     */
    public void setSprite(PImage sprite){
        this.sprite = sprite;
    }
    
    /**
     * Attacks any enemy within a short range of the character.
     * @param enemies A list of enemies currently active in the scene
     */
    public void attackEnemies(ArrayList<Enemy> enemies) {
    int attackRange = 50; 
    for (Enemy enemy : enemies) {
        if (enemy.isAlive()) {
            // Calculate distance between this character and the enemy
            double dx = enemy.getX() - this.x;
            double dy = enemy.getY() - this.y;
            double distance = Math.sqrt(dx * dx + dy * dy);

             // If enemy is within range, apply damage
            if (distance <= attackRange) {
                enemy.takeDamage(20); 
            }
        }
    }
}
    
}
