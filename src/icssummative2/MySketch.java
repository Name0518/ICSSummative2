package icssummative2;

import processing.core.PApplet;

public class MySketch extends PApplet {
    private SceneManager[] scenes;
    private int currentSceneIndex;

    public static void main(String[] args) {
        PApplet.main("icssummative2.MySketch");
    }

    public void settings() {
        size(1280, 768);
    }

    public void setup() {
        scenes = new SceneManager[] {
            new SceneManager("dialogue/scene1.txt", "scenes/pavillion.png"),
            new SceneManager("dialogue/scene2.txt", "scenes/throneroom.png"),
            new SceneManager("dialogue/scene3.txt", "scenes/courtyard.png")
        };
        currentSceneIndex = 0;
        scenes[currentSceneIndex].setUpScene(this); 
        textSize(24);
    }

    public void draw() {
        scenes[currentSceneIndex].playScene(this);
    }

    public void mousePressed() {
         SceneManager currentScene = scenes[currentSceneIndex];
        if (currentScene.isFinished()) {
            // Move to next scene
            currentSceneIndex++;
            if (currentSceneIndex < scenes.length) {
                scenes[currentSceneIndex].setUpScene(this);
            } else {
                // End of game or loop back
                currentSceneIndex = 0;
                scenes[currentSceneIndex].setUpScene(this);
            }
        } else {
            currentScene.nextDialogue();
        }
    }
    }

