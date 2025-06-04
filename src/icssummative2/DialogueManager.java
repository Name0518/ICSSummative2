/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package icssummative2;
import processing.core.PApplet;
import java.util.*;
/**
 *
 * @author Stikb
 */
public class DialogueManager {
    private String speaker;
    private String text;
    
    public DialogueManager(String speaker, String text) {
        this.speaker = speaker;
        this.text = text;
    }

    public String getSpeaker() {
        return speaker;
    }

    public String getText() {
        return text;
    }
    public void display(PApplet app, int x, int y) {
        app.fill(200, 200, 0);
        app.text(speaker, x, y);
        app.fill(255);
        app.text(text, x, y + 40, app.width - 40, app.height - 100);
    }
    
    public void loadCharacterImage(){
        
    }
    
}
