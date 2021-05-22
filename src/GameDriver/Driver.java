package GameDriver;

import UserInterface.Menu;
import UserInterface.SmartSquare;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * @author Hephaest
 * @since 3/21/2019 8:41 PM
 * This class is used to run the minesweeper program.
 */
public class Driver {
    public static void main(String[] Args) {
        // Start the game with a menu.
        new Menu("Minesweeper");
        playMusic();
    }


    public static void playMusic() {
        try {

            File f = new File(SmartSquare.class.getResource("/music1.wav").getFile());
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(f);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
