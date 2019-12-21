import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.File;
import java.util.Random;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Transition {
    public static Clip makeClip(String path) {
        try {
            File in = new File(path);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(in);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            return clip;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void play(Clip clip) {
        clip.start();
    }
    public static void stop(Clip clip) {
        if (clip != null) {
            clip.stop();
            clip = null;
        }
    }
    public static Clip makeRandClip(String path) {
        Random rand = new Random();
        File[] files = new File(path).listFiles();
        File file = files[rand.nextInt(files.length)];
        return makeClip(file.getPath());
    }
    public static void enter(Node node, int duration) {
        FadeTransition ft = new FadeTransition(Duration.millis(duration), node);
        ft.setFromValue(0);
        ft.setToValue(1.0);
        ft.play();
    }
    public static void end(Node node, int duration, Scene nextScene) {
        FadeTransition ft = new FadeTransition(Duration.millis(duration), node);
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.setOnFinished(e -> moveTo(nextScene));
        ft.play();
    }
    public static void stay(Node node, int duration, int cycles, Scene nextScene) {
        FadeTransition ft = new FadeTransition(Duration.millis(duration), node);
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.setCycleCount(cycles);
        ft.setAutoReverse(true);
        ft.setOnFinished(e -> moveTo(nextScene));
        ft.play();
    }
    private static void moveTo(Scene nextScene) {
        Stage mainStage = GameGUI.getStage();
        mainStage.setScene(nextScene);
    }
    public static void stay(Node node, int duration, int cycles) {
        FadeTransition ft = new FadeTransition(Duration.millis(duration), node);
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.setCycleCount(cycles);
        ft.setAutoReverse(true);
        ft.play();
    }
}
