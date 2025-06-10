package icssummative2;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;
import static processing.core.PConstants.DOWN;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.RIGHT;
import static processing.core.PConstants.UP;

public class CombatScene extends SceneManager {
    private String backgroundPath;
    private ArrayList<Enemy> enemies;
    private PImage backgroundImg;
    private PlayableCharacter character;
    private PApplet sketch;

    public CombatScene(String backgroundPath) {
        super(backgroundPath);
        this.backgroundPath = backgroundPath;
        enemies = new ArrayList<>();
    }

    @Override
    public void setUpScene(PApplet sketch) {
        this.sketch = sketch;
        enemies.clear();

        backgroundImg = this.sketch.loadImage(backgroundPath);
        character = new PlayableCharacter(100, 100, 64, 64, "characters/Lu Bu.png",sketch);
        // Add enemies (x,y,
        enemies.add(new Enemy(800, 300, 64, 64, 100, 10, 1.0f, 100, "characters/goomba.png", sketch));
        enemies.add(new Enemy(600, 500, 64, 64, 100, 5, 0.5f, 80, "characters/goomba.png", sketch));
    }

    @Override
    public void playScene(PApplet sketch) {
        sketch.image(backgroundImg, 0, 0, sketch.width, sketch.height);

        // Movement input
        if (sketch.keyPressed) {
            if (sketch.keyCode == LEFT) 
                character.move(-1, 0);
            if (sketch.keyCode == RIGHT) 
                character.move(1, 0);
            if (sketch.keyCode == UP) 
                character.move(0, -1);
            if (sketch.keyCode == DOWN) 
                character.move(0, 1);
        }

        character.display(sketch);

        // Enemy logic
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
        sketch.fill(255, 0, 0);
        sketch.textSize(48);
        sketch.text("You Died", sketch.width / 2 - 100, sketch.height / 2);
    } else if (enemies.isEmpty()) {
        sketch.fill(0, 255, 0);
        sketch.textSize(48);
        sketch.text("Victory!", sketch.width / 2 - 100, sketch.height / 2);
    }
}
    public void mousePressed() {
    if (sketch.mouseButton == PApplet.LEFT) {
       
    }
}

}

