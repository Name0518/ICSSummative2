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
public class DialogueManager {
    private String speaker;
    private String text;
    
    /**
     * Constructs a DialogueManager object with a speaker and a line of dialogue.
     *
     * @param speaker The name of the character speaking
     * @param text    The line of dialogue to be displayed
     */
    public DialogueManager(String speaker, String text) {
        this.speaker = speaker;
        this.text = text;
    }
    
    /**
     * Gets speaker name
     * @return The speaker's name
     */
    public String getSpeaker() {
        return speaker;
    }

    /**
     * Gets dialogue text
     * 
     * @return The dialogue text
     */
    public String getText() {
        return text;
    }
    
    /**
     * Displays speaker name and dialogue text on the screen
     *
     * @param app PApplet instance
     * @param x   The x-coordinate of the text box
     * @param y   The y-coordinate for the speaker name; dialogue appears below
     */
    public void display(PApplet app, int x, int y) {
        // Draw speaker name in yellow
        app.fill(200, 200, 0);
        app.text(speaker, x, y);
        
        // Draw dialogue text in white, wrapping within screen bounds
        app.fill(255);
        app.text(text, x, y + 40, app.width - 40, app.height - 100);
    } 
}
