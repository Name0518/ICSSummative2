package icssummative2;

import processing.core.PApplet;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import processing.core.PImage;

public class MySketch extends PApplet {
    
    private ArrayList<DialogueManager> dialogueLines;
    private int currentIndex;
    private PImage backgroundImg;
    private static final String DIALOGUE_FILE = "dialogue.txt";
   

    public static void main(String[] args) {
        PApplet.main("icssummative2.MySketch");
    }
    
    public void loadDialogueFromFile(String filePath){
        try{
            Scanner reader = new Scanner(new File(filePath));
            while (reader.hasNextLine()){
                 String text = reader.nextLine();
                 String[] parts = text.split(":");
                 String speaker = parts[0].trim();
                 String dialogue = parts[1].trim();
                 if (speaker.equalsIgnoreCase("Dong Zhuo") || speaker.equalsIgnoreCase("Lu Bu")) {
                    dialogueLines.add(new EmphasizedLine(speaker, dialogue));
                    } 
                 else {
                    dialogueLines.add(new DialogueManager(speaker, dialogue));
                    }
                 
            }
         } catch (IOException e) {
            System.out.println("Dialogue file not found.");
        }
        
    }
    
    public DialogueManager getCurrentDialogue() {
        if (currentIndex >= 0 && currentIndex < dialogueLines.size()) {
            return dialogueLines.get(currentIndex);
        }
        return null;
    }
    
    public void nextDialogue() {
        currentIndex++;
        if (currentIndex >= dialogueLines.size()) {
            currentIndex = 0;
        }
        
        
    }
    
    public void settings() {
        size(1280, 768);
    }

    public void setup() {
        dialogueLines = new ArrayList<>();
        currentIndex = 0;
        loadDialogueFromFile(DIALOGUE_FILE);
        textSize(24);
        backgroundImg = loadImage("pavillion.png", "png");
    }

    public void draw() {
        backgroundImg.resize(1280, 768);
        image(backgroundImg, 0, 0);

        DialogueManager current = getCurrentDialogue();
        if (current != null) {
            image(loadImage("characters/" + current.getSpeaker() + ".png"), 400, 0);
            current.display(this, 210, 600);
            
        } else {
            fill(255);
            text("No dialogue loaded.", 20, height/2);
        }
    }
    

    
   
    public void mousePressed() {
        nextDialogue();
    }
    
    
}
