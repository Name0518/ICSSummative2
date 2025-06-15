/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package icssummative2;
import processing.core.PApplet;
/**
 *
 * @author Stikb
 */
public class EmphasizedLine extends DialogueManager {
    
    /**
     * Constructs EmphasizedLine with given speaker and text
     * 
     * @param speaker The name of the character speaking
     * @param text    The line of dialogue to emphasize
     */
    public EmphasizedLine(String speaker, String text) {
        super(speaker, text);
    }

    /**
     * Displays emphasized dialogue by changing speaker name color to red
     * 
     * @param app The PApplet instance to draw on
     * @param x   The x-coordinate of the dialogue
     * @param y   The y-coordinate for the speaker name; text appears below
     */
    @Override
    public void display(PApplet app, int x, int y) {
        app.fill(255, 0, 0); // red speaker name
        app.text(getSpeaker(), x, y);
        
        //white dialogue box
        app.fill(255);
        app.text(getText(), x, y + 40, app.width - 40, app.height - 100);
    }
}
