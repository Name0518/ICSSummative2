package icssummative2;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;
import static processing.core.PConstants.DOWN;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.RIGHT;
import static processing.core.PConstants.UP;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class CombatScene extends SceneManager {
    private String backgroundPath;
    private ArrayList<Enemy> enemies;
    private PImage backgroundImg;
    private PlayableCharacter character;
    private PApplet sketch;
    private boolean isGameOver = false;
    private final int highScore;
    private int currentScore;

    public CombatScene(String backgroundPath) {
        super(backgroundPath);
        this.backgroundPath = backgroundPath;
        highScore = Integer.parseInt(getHighScore());
    }

    @Override
    public void setUpScene(PApplet sketch) {
        this.sketch = sketch;
        enemies = new ArrayList<>();
        isGameOver = false;
        backgroundImg = this.sketch.loadImage(backgroundPath);
        character = new PlayableCharacter(100, 100, 64, 64, "characters/Lu Bu.png",sketch);
        // Add enemies (x,y,
        enemies.add(new Enemy(800, 300, 64, 64, 100, 10, 1.0f, 100, "characters/goomba.png", sketch));
        enemies.add(new Enemy(600, 500, 64, 64, 100, 5, 0.5f, 80, "characters/goomba.png", sketch));
    }

    @Override
    public void playScene(PApplet sketch) {
        sketch.image(backgroundImg, 0, 0, sketch.width, sketch.height);
        sketch.fill(0, 0, 0);
        sketch.text("Objective: Defeat all enemies", 100, 150);
        sketch.text("Use arrow keys to move and LMB to attack",100, 50);
        sketch.text("High Score: " + Integer.toString(highScore) ,1000, 50);
        if (sketch.keyPressed) {
            if (sketch.keyCode == LEFT) 
                character.move(-3, 0);
            if (sketch.keyCode == RIGHT) 
                character.move(3, 0);
            if (sketch.keyCode == UP) 
                character.move(0, -3);
            if (sketch.keyCode == DOWN) 
                character.move(0, 3);
        }

        character.display(sketch);
        for (int i = enemies.size() - 1; i >= 0; i--) {
        Enemy enemy = enemies.get(i);
        if (enemy.isAlive()) {
            enemy.moveToward(character.getX(), character.getY());
            enemy.attack(character, sketch.millis());
            enemy.display(sketch);
        } else {
            enemies.remove(i);
        }
    }

    if (!character.isAlive()) {
        isGameOver = true;
        sketch.fill(255, 0, 0);
        sketch.text("You Died\nClick to Restart", sketch.width / 2 - 150, sketch.height / 2);
    } else if (enemies.isEmpty()) {
        currentScore = character.getHealth() * Enemy.enemyCount;
        sketch.fill(0, 255, 0);
        sketch.text("Victory! Score: " + Integer.toString(currentScore) , sketch.width / 2 - 100, sketch.height / 2);
        writeHighScore(currentScore);
    }
}
    public void mousePressed() {
    if (isGameOver) {
            setUpScene(sketch); 
        } else if (sketch.mouseButton == PApplet.LEFT) {
            character.attackEnemies(enemies);
        }
}
    
    @Override
    public boolean isFinished() {
        return enemies.isEmpty();

}
    
    public String getHighScore(){
        try{
        Scanner scanner = new Scanner(new File("highscore.txt"));
        String score = scanner.nextLine();
        scanner.close();
        return score;
        }catch(FileNotFoundException e){
            System.out.println("Error: high score file not found");
            return "";
        }
}
    
    public void writeHighScore(int score){
        try {
        int oldHighScore = Integer.parseInt(getHighScore());
        if (score > oldHighScore) {
            FileWriter writer = new FileWriter("highScore.txt"); 
            writer.write(Integer.toString(score));
            writer.close();
        }
    } catch (IOException e) {
        System.out.println("Error writing high score: " + e.getMessage());
    } 
    }
   
}
