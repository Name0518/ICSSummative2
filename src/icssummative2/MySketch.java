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
        // Load scenes in order: intro, dialogue scenes, combat, and ending
        scenes.add(new SceneManager("dialogue/intro.txt", "scenes/intro.png"));
        scenes.add(new SceneManager("dialogue/scene1.txt", "scenes/changan.png"));
        scenes.add(new SceneManager("dialogue/scene2.txt", "scenes/pavillion.png"));
        scenes.add(new SceneManager("dialogue/scene3.txt", "scenes/throneroom.png"));
        scenes.add(new SceneManager("dialogue/scene4.txt", "scenes/chamber.png"));
        scenes.add(new CombatScene("scenes/courtyardbirdseye.png"));
        scenes.add(new SceneManager("dialogue/scene5.txt", "scenes/bedroom.png"));
        scenes.add(new SceneManager("dialogue/scene6.txt", "scenes/epilogue1.png"));
        scenes.add(new SceneManager("dialogue/scene7.txt", "scenes/epilogue2.png"));
        scenes.add(new SceneManager("dialogue/ending.txt", "scenes/epilogue2.png"));
        
        currentSceneIndex = 0;
        scenes.get(currentSceneIndex).setUpScene(this); //Start and setup first scene
        textSize(24);
    }

    public void draw() {
        scenes.get(currentSceneIndex).playScene(this);
    }

    public void mousePressed() {
        SceneManager currentScene = scenes.get(currentSceneIndex);
        if (currentScene instanceof CombatScene) {
            ((CombatScene) currentScene).mousePressed();// If it's a combat scene, call combatscene mousePressed method
    }
        // Check if the current scene is finished
        if (currentScene.isFinished()) {
            // Move to next scene
            currentSceneIndex++;
            if (currentSceneIndex < scenes.size()) {
                scenes.get(currentSceneIndex).setUpScene(this); // Set up the next scene
            } else {
                exit(); //Exit application
            }
        } else {
            currentScene.nextDialogue(); //go to the next line
        }
    }
}
    

    

   

