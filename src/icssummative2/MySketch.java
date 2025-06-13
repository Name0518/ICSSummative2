package icssummative2;

import processing.core.PApplet;
import java.util.ArrayList; 

public class MySketch extends PApplet {
    private ArrayList<SceneManager> scenes;
    private int currentSceneIndex;

    public static void main(String[] args) {
        PApplet.main("icssummative2.MySketch");
    }

    public void settings() {
        size(1280, 768);
    }

    public void setup() {
        scenes = new ArrayList<>();
        scenes.add(new SceneManager("dialogue/scene1.txt", "scenes/pavillion.png"));
        scenes.add(new SceneManager("dialogue/scene2.txt", "scenes/throneroom.png"));
        scenes.add(new SceneManager("dialogue/scene3.txt", "scenes/courtyard.png"));
        scenes.add(new CombatScene("scenes/courtyardbirdseye.png"));
        currentSceneIndex = 0;
        scenes.get(currentSceneIndex).setUpScene(this); 
        textSize(24);
    }

    public void draw() {
        scenes.get(currentSceneIndex).playScene(this);
    }

    public void mousePressed() {
        SceneManager currentScene = scenes.get(currentSceneIndex);
        if (currentScene instanceof CombatScene) {
            ((CombatScene) currentScene).mousePressed();
    }
        if (currentScene.isFinished()) {
            // Move to next scene
            currentSceneIndex++;
            if (currentSceneIndex < scenes.size()) {
                scenes.get(currentSceneIndex).setUpScene(this);
            } else {
                // End of game or loop back
                currentSceneIndex = 0;
                scenes.get(currentSceneIndex).setUpScene(this);
            }
        } else {
            currentScene.nextDialogue();
        }
    }
}
    

    

   

