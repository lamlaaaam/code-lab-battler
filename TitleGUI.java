import javafx.application.Platform;

import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

import javafx.stage.Stage;

import javax.sound.sampled.Clip;

public class TitleGUI {
    private static final int GAME_WIDTH = 1280;
    private static final int GAME_HEIGHT = 720;
    private static Clip bgm = null;
    private static int selection = 0;
    public static Clip getClip() {
        return bgm;
    }
    public static Scene createScene() {
        Transition.stop(bgm);
        Transition.stop(InGameGUI.getbgm());
        Transition.stop(InGameGUI.getbattle());
        bgm = Transition.makeRandClip("./music/menu/bgm/");
        bgm.loop(Clip.LOOP_CONTINUOUSLY);
        Transition.play(bgm);
        //Create window and background
        Group root = new Group();
        Image bgImage = new Image(TitleGUI.class.getResource("/images/mainbg.jpg").toExternalForm());
        ImagePattern bgPattern = new ImagePattern(bgImage);
        Scene scene = new Scene(root, GAME_WIDTH, GAME_HEIGHT, bgPattern);
        // Title text
        Image titleImg = new Image(TitleGUI.class.getResource("/images/title.png").toExternalForm());
        ImageView titleImgView = new ImageView(titleImg);
        titleImgView.setX(300);
        titleImgView.setY(110);
        root.getChildren().add(titleImgView);
        // Logo
        Image logoImg = new Image(TitleGUI.class.getResource("/images/logo.png").toExternalForm());
        ImageView logoImgView = new ImageView(logoImg);
        logoImgView.setX(110);
        logoImgView.setY(10);
        logoImgView.setFitHeight(190);
        logoImgView.setFitWidth(190);
        root.getChildren().add(logoImgView);
        // Menu buttons
        Group menuButtons = new Group();
        Image startImg = new Image(TitleGUI.class.getResource("/images/startmenu.png").toExternalForm());
        Image quitImg = new Image(TitleGUI.class.getResource("/images/quitmenu.png").toExternalForm());
        ImageView startImgView = new ImageView(startImg);
        ImageView quitImgView = new ImageView(quitImg);
        startImgView.setX(510);
        startImgView.setY(260);
        quitImgView.setX(440);
        quitImgView.setY(380);
        menuButtons.getChildren().addAll(startImgView, quitImgView);
        root.getChildren().add(menuButtons);
        // Menu arrows
        Image arrowImg = new Image(TitleGUI.class.getResource("/images/menuarrow.png").toExternalForm());
        ImageView arrowImgView = new ImageView(arrowImg);
        arrowImgView.setX(350);
        arrowImgView.setY(250);
        menuButtons.getChildren().add(arrowImgView);
        // Event handler for arrows
        EventHandler<KeyEvent> keyHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN) {
                    Clip menu = Transition.makeClip("./music/menu/sfx/move.wav");
                    Transition.play(menu);
                    selection = selection == 1 ? 0 : 1;
                    arrowImgView.setY(selection == 0
                                      ? 250
                                      : 370);
                }
                if (e.getCode() == KeyCode.SPACE || e.getCode() == KeyCode.ENTER) {
                    Clip click = Transition.makeClip("./music/menu/sfx/click.wav");
                    Transition.play(click);
                    // SELECTION HANDLER
                    if (selection == 0) {
                        // START GAME
                        scene.removeEventFilter(KeyEvent.KEY_PRESSED, this);
                        Transition.end(root, 1000, NameGUI.createScene());
                    } else {
                        Platform.exit();
                    }
                }
            }
        };   
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyHandler);        

        // Fade in
        Transition.enter(root, 1000);
        return scene;
    } 
}
