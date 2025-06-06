package icssummative2;

import processing.core.PApplet;
import processing.core.PImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SceneManager {
    private final String BACKGROUND_PATH;
    private final String FILE_PATH;
    private ArrayList<DialogueManager> dialogueLines;
    private int currentIndex = 0;
    private PImage backgroundImg;
    
    public SceneManager(String filePath, String backgroundPath) {
        FILE_PATH = filePath;
        BACKGROUND_PATH = backgroundPath;
    }
    
    public SceneManager(String backgroundPath) {
        FILE_PATH = "blank.txt";
        BACKGROUND_PATH = backgroundPath;
    }

    public DialogueManager getCurrentDialogue() {
        if (currentIndex >= 0 && currentIndex < dialogueLines.size()) {
            return dialogueLines.get(currentIndex);
        }
        return null;
    }

    public void loadDialogueFromFile() {
        dialogueLines = new ArrayList<>();
        try {
            Scanner reader = new Scanner(new File(FILE_PATH));
            while (reader.hasNextLine()) {
                String text = reader.nextLine();
                String[] parts = text.split(":");
                String speaker = parts[0].trim();
                String dialogue = parts[1].trim();

                if (speaker.equalsIgnoreCase("Dong Zhuo") || speaker.equalsIgnoreCase("Lu Bu")) {
                    dialogueLines.add(new EmphasizedLine(speaker, dialogue));
                } else {
                    dialogueLines.add(new DialogueManager(speaker, dialogue));
                }
            }
        } catch (IOException e) {
            System.out.println("Dialogue file not found: " + FILE_PATH);
        }
    }

    public void nextDialogue() {
        currentIndex++;
    }

    public void setUpScene(PApplet sketch) {
        currentIndex = 0;
        loadDialogueFromFile();
        backgroundImg = sketch.loadImage(BACKGROUND_PATH);
    }

    public void playScene(PApplet sketch) {
        sketch.image(backgroundImg, 0, 0, sketch.width, sketch.height);

        DialogueManager current = getCurrentDialogue();
        if (current != null) {
            sketch.image(sketch.loadImage("characters/" + current.getSpeaker() + ".png"), 400, 0);
            sketch.fill(0, 0, 0, 180);
            sketch.noStroke();
            sketch.rect(0, sketch.height - 240, sketch.width, 240);
            current.display(sketch, 210, 600);
        } else {
            sketch.fill(255);
            sketch.text(" ", 20, sketch.height / 2);
        }
    }
    
    public boolean isFinished() {
    return currentIndex >= dialogueLines.size();
}
    


    
}
