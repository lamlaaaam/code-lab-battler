import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;

import javafx.stage.Stage;

import javax.sound.sampled.Clip;

public class NameGUI {
    private static final int GAME_WIDTH = 1280;
    private static final int GAME_HEIGHT = 720;
    private static final int nameLeft = 300;
    private static final int nameRight = 1070;
    private static String name;
    private static int nameX;
    private static int nameY;
    private static Text nameGUI;
    private static Player player;

    public static Player getPlayer() {
        return player;
    }
    public static Scene createScene() {
        // Initialize
        name = "";
        nameX = nameLeft;
        nameY = 594;
        nameGUI = new Text(nameX, nameY, name);
        nameGUI.setTextAlignment(TextAlignment.CENTER);
        // Create window and background
        Group root = new Group();
        Image bgImage = new Image(NameGUI.class.getResource("/images/mainbg.jpg").toExternalForm());
        ImagePattern bgPattern = new ImagePattern(bgImage);
        Scene scene = new Scene(root, GAME_WIDTH, GAME_HEIGHT, bgPattern);
        // Name request by YACMB
        Image yacmbAskImg = new Image(NameGUI.class.getResource("/images/yacmbaskname.png").toExternalForm());
        ImageView yacmbAskImgView = new ImageView(yacmbAskImg);
        yacmbAskImgView.setX(300);
        yacmbAskImgView.setY(100);
        root.getChildren().add(yacmbAskImgView);
        // Name input box
        Image nameInImg = new Image(NameGUI.class.getResource("/images/dialoguebox.png").toExternalForm());
        ImageView nameInImgView = new ImageView(nameInImg);
        nameInImgView.setFitWidth(800);
        nameInImgView.setFitHeight(100);
        nameInImgView.setX(230);
        nameInImgView.setY(520);
        root.getChildren().add(nameInImgView);
        // Handle text input
        nameGUI.setFont(Font.loadFont("file:fonts/SF Pixelate.ttf", 60));
        root.getChildren().add(nameGUI);
        EventHandler<KeyEvent> nameInput = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                Clip kb = Transition.makeClip("./music/menu/sfx/kb.wav");
                kb.setMicrosecondPosition(500000);
                if (e.getCode().isLetterKey() && name.length() < 13) {
                    // Enter letter
                    Transition.play(kb);
                    name += e.getText().toUpperCase();
                    nameGUI.setText(name);
                }
                if (e.getCode() == KeyCode.SPACE && name.length() < 13) {
                    // Handle space
                    Transition.play(kb);
                    name += " ";
                    nameGUI.setText(name);
                }
                if (e.getCode() == KeyCode.ENTER) {
                    // Handle submission
                    Clip enter = Transition.makeClip("./music/menu/sfx/click.wav");
                    Transition.play(enter);
                    player = new Player(name);
                    scene.removeEventFilter(KeyEvent.KEY_PRESSED, this);
                    Transition.end(root, 1000, InGameGUI.createScene());
                }
                if (e.getCode() == KeyCode.BACK_SPACE && name.length() > 0) {
                    // Handle backspace
                    Transition.play(kb);
                    name = name.substring(0, name.length() - 1);
                    nameGUI.setText(name);
                }
            }
        };
        scene.addEventFilter(KeyEvent.KEY_PRESSED, nameInput);

        Transition.enter(root, 1000);
        return scene;
    }
}
