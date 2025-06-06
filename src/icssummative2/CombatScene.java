/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package icssummative2;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

/**
 *
 * @author Stikb
 */
public class CombatScene extends SceneManager {
    private String backgroundPath;
    private ArrayList<Enemy> enemies;
    private PImage backgroundImg;
    
    public CombatScene(String filePath, String backgroundPath) {
        super(backgroundPath);
        enemies = new ArrayList<>();
    }

    @Override
    public void playScene(PApplet sketch) {
        // Draw background like normal
        sketch.image(backgroundImg, 0, 0, sketch.width, sketch.height);

        // TODO: Add actual combat logic here
    }
    
    @Override
    public void setUpScene(PApplet sketch) {
        backgroundImg = sketch.loadImage(backgroundPath);
    }
    // Optional: Add combat-specific behavior or state
}
