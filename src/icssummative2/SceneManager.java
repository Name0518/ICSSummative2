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
    
    /**
     * Constructs a SceneManager with dialogue and background
     * @param filePath Path to dialogue file
     * @param backgroundPath Path to background image
     */
    public SceneManager(String filePath, String backgroundPath) {
        FILE_PATH = filePath;
        BACKGROUND_PATH = backgroundPath;
    }
    
    /**
     * Constructs a SceneManager with just a background and a default empty dialogue file.
     * @param backgroundPath Path to background image
     */
    public SceneManager(String backgroundPath) {
        FILE_PATH = "scenes/blank.txt";
        BACKGROUND_PATH = backgroundPath;
    }

    /**
     * Returns the current line of dialogue.
     * @return DialogueManager object representing the current line
     */
    public DialogueManager getCurrentDialogue() {
        if (currentIndex >= 0 && currentIndex < dialogueLines.size()) {
            return dialogueLines.get(currentIndex);
        }
        return null;
    }

    /**
     * Loads dialogue lines from the file, splitting by speaker and line
     * Uses EmphasizedLine for key characters.
     */
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

    /**
     * Moves to the next dialogue line
     */
    public void nextDialogue() {
        currentIndex++;
    }

    /**
     * Loads the background and dialogue file for this scene
     * @param sketch The PApplet used for rendering and loading images
     */
    public void setUpScene(PApplet sketch) {
        currentIndex = 0;
        loadDialogueFromFile();
        backgroundImg = sketch.loadImage(BACKGROUND_PATH);
    }

    /**
     * Draws the background and current dialogue line to the screen
     * @param sketch The PApplet used for rendering
     */
    public void playScene(PApplet sketch) {
        sketch.image(backgroundImg, 0, 0, sketch.width, sketch.height);

        DialogueManager current = getCurrentDialogue();
        if (current != null) {
            sketch.image(sketch.loadImage("characters/" + current.getSpeaker() + ".png"), 400, 0);
            sketch.fill(0, 0, 0, 180);
            sketch.noStroke();
            sketch.rect(0, sketch.height - 240, sketch.width, 240);
            showDialogue(current, sketch);
        } else {
            sketch.fill(255);
            sketch.text(" ", 20, sketch.height / 2);
        }
    }
    
    /**
     * Checks if all dialogue lines have been displayed
     * @return true if the scene is done, false otherwise
     */
    public boolean isFinished() {
        return currentIndex >= dialogueLines.size();
    }
    
     /**
     * Displays the given dialogue line on screen.
     * @param line can be DialogueManager or EmphasizedLine
     * @param sketch PApplet used for drawing
     */
    public void showDialogue(DialogueManager line, PApplet sketch){
        line.display(sketch, 210, 600);
    }
}
