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

    public EmphasizedLine(String speaker, String text) {
        super(speaker, text);
    }

    @Override
    public void display(PApplet app, int x, int y) {
        app.fill(255, 0, 0); // red speaker name
        app.text(getSpeaker(), x, y);
        app.fill(255);
        app.text(getText(), x, y + 40, app.width - 40, app.height - 100);
    }
    
    
    
}
