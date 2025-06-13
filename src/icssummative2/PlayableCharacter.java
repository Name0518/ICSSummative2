package icssummative2;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

public class PlayableCharacter {
    private int x, y;
    private int speed;
    private int health;
    private int width, height;
    private PImage sprite;

    public PlayableCharacter(int x, int y, int width, int height, String spritePath, PApplet app) {
        this.x = x;
        this.y = y;
        this.speed = 5;
        this.health = 100;
        this.width = width;
        this.height = height;
        this.sprite = app.loadImage(spritePath);
    }

    public void display(PApplet app) {
        app.image(sprite, x, y, width, height);
        app.fill(0, 255, 0);
        app.rect(x, y - 10, width * (health / 100.0f), 5); // health bar
    }

    public void move(int dx, int dy) {
        x += dx * speed;
        y += dy * speed;
    }

    public void takeDamage(int dmg) {
        health -= dmg;
        if (health < 0) {
            health = 0;
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int getX() { 
        return x; 
    }
    public int getY() { 
        return y; 
    }
    
    public int getHealth(){
        return health;
    }
    
    public void attackEnemies(ArrayList<Enemy> enemies) {
    int attackRange = 50; 
    for (Enemy enemy : enemies) {
        if (enemy.isAlive()) {
            float dx = enemy.getX() - this.x;
            float dy = enemy.getY() - this.y;
            float distance = (float) Math.sqrt(dx * dx + dy * dy);

            if (distance < attackRange) {
                enemy.takeDamage(20); 
            }
        }
    }
}
    
}
