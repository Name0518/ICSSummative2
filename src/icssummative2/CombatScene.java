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

/**
 * Represents a combat scene in the game where the player fights against enemies.
 * Manages enemy and player behavior, animation, score, and game state.
 */
public class CombatScene extends SceneManager {
    private String backgroundPath;
    private ArrayList<Enemy> enemies;
    private PImage backgroundImg;
    private PlayableCharacter character;
    private PApplet sketch;
    private boolean isGameOver = false;
    private final int highScore;
    private int currentScore;
    private PImage[][] animations; // Stores directional animation frames
    private int[][] frames = {
  {0, 1, 2}, 
  {0, 1, 2},   
  {0, 1, 2},  
  {0, 1, 2},  
  {0, 1, 2},  
  {0, 1, 2},  
  {0, 1, 2},  
};
    private int direction = 6; //Starting direction of player
    private int frameIndex = 0; //animation frame index
    private int frameDelay = 5; //delay between frames
    private int frameCounter = 0; //counter for frame delay
    private boolean isMoving = false;
    private int lastMousePressedID; //determines last directional frame

    /**
     * Constructor for CombatScene
     * @param backgroundPath The file path for the scene's background image
     */
    public CombatScene(String backgroundPath) {
        super(backgroundPath);
        this.backgroundPath = backgroundPath;
        highScore = Integer.parseInt(getHighScore());
    }
    
    /**
     * Sets up the scene by initializing enemies, loading images and character.
     * @param sketch The PApplet instance used for drawing and loading resources.
     */

    @Override
    public void setUpScene(PApplet sketch) {
        this.sketch = sketch;
        enemies = new ArrayList<>();
        isGameOver = false;
        
        //loads background image
        backgroundImg = this.sketch.loadImage(backgroundPath);
        
        //Initalize player
        character = new PlayableCharacter(100, 100, 64, 64, "characters/Lu Bu.png",sketch);
        
        //Initialize enemies
        enemies.add(new Enemy(800, 300, 64, 64, 100, 10, 1.0f, 100, "sprites/enemy.png", sketch));
        enemies.add(new Enemy(600, 500, 64, 64, 100, 5, 0.5f, 80, "sprites/enemy.png", sketch));
        
        //Initialize frame list
        animations = new PImage[7][3];
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                String folder = "sprites/" + i + "/";
                String filename = frames[i][j] + ".png";
                animations[i][j] = sketch.loadImage(folder + filename);
    }
  }
    }
    
    /**
     * Plays the scene. Handles character/enemy updates, rendering, animation, and game states.
     * @param sketch The PApplet instance used to draw the scene.
     */
    @Override
    public void playScene(PApplet sketch) {
        
        // Draw background and text
        sketch.image(backgroundImg, 0, 0, sketch.width, sketch.height);
        sketch.fill(255, 255, 255);
        sketch.text("Objective: Defeat all enemies", 100, 150);
        sketch.text("Use arrow keys to move and LMB to attack",100, 50);
        sketch.text("High Score: " + Integer.toString(highScore) ,1000, 50);
        
        // Handle movement input
        if (sketch.keyPressed) {
            isMoving = true;
            if (sketch.keyCode == LEFT) {
                character.move(-3, 0);
                direction = 4;
                lastMousePressedID = 0;
            }
            if (sketch.keyCode == RIGHT) {
                character.move(3, 0);
                direction = 0;
                lastMousePressedID = 1;
            }
            if (sketch.keyCode == UP) {
                character.move(0, -3);
                direction = 6;
                lastMousePressedID = 2;
            }
            if (sketch.keyCode == DOWN) {
                character.move(0, 3);
                direction = 5;
                lastMousePressedID = 3;
            }
                
        }
        
        // Handle animation frame switching
        if (isMoving) {
            frameCounter++;
        if (frameCounter >= frameDelay) {
            frameCounter = 0;
            frameIndex = (frameIndex + 1) % 3;
        }
        } else {
            frameIndex = 0;
        }
        
        // Set current sprite frame and display character
        character.setSprite(animations[direction][frameIndex]);
        character.display(sketch);
        
        // Handle enemy behavior
        for (int i = enemies.size() - 1; i >= 0; i--) {
        Enemy enemy = enemies.get(i);
        if (enemy.isAlive()) {
            enemy.moveToward(character.getX(), character.getY());
            enemy.attack(character, sketch.millis());
            enemy.display(sketch);
        } else {
            enemies.remove(i); // Remove dead enemies
        }
    }

    // Display end-game messages
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
    
    /**
     * Called when the mouse is pressed, handles restarting the game or attacking enemies
     */
    public void mousePressed() {
    if (isGameOver) {
            setUpScene(sketch);  // Restart scene
        } else if (sketch.mouseButton == PApplet.LEFT) {
            character.attackEnemies(enemies); // Perform attack
            if (lastMousePressedID == 0){
                direction = 1;
            }
            if (lastMousePressedID == 1){
                direction = 1;
            }
            if (lastMousePressedID == 2){
                direction = 2;
            }
            if (lastMousePressedID == 3){
                direction = 3;
            }
        }
}
    
    /**
     * Returns true if all enemies have been defeated.
     * @return true if scene is finished.
     */
    @Override
    public boolean isFinished() {
        return enemies.isEmpty();

}
    
    /**
     * Reads the high score from a flat file.
     * @return The high score as a string.
     */
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
    
    /**
     * Writes a new high score to the file if it beats the old score.
     * @param score The new score to write.
     */
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
