package icssummative2;

import processing.core.PApplet;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class MySketch extends PApplet {
    
    private ArrayList<DialogueManager> dialogueLines;
    private int currentIndex;
    
    private static final String DIALOGUE_FILE = "src/icssummative2/dialogue.txt";

    public void settings() {
        size(800, 400);
    }

    public void setup() {
        dialogueLines = new ArrayList<>();
        currentIndex = 0;
        loadDialogueFromFile(DIALOGUE_FILE);
        textSize(24);
    }

    public void draw() {
        background(50);

        DialogueManager current = getCurrentDialogue();
        if (current != null) {
            current.display(this, 20, 40);
        } else {
            fill(255);
            text("No dialogue loaded.", 20, height/2);
        }
    }
    
    public void mousePressed() {
        nextDialogue();
    }

    public static void main(String[] args) {
        PApplet.main("icssummative2.MySketch");
    }
    
    public void loadDialogueFromFile(String filePath){
        try{
            Scanner reader = new Scanner(new File(filePath));
            String text = reader.nextLine();
            while (reader.hasNextLine()){
                 String[] parts = text.split(":");
                 String speaker = parts[0].trim();
                 String dialogue = parts[1].trim();
                 if (speaker.equalsIgnoreCase("Lu Bu") || speaker.equalsIgnoreCase("Diaochan")) {
                    dialogueLines.add(new EmphasizedLine(speaker, text));
                    } 
                 else {
                    dialogueLines.add(new DialogueManager(speaker, text));
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
    
    
}
