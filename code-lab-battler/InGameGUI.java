import javafx.application.Platform;

import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.shape.Rectangle;

import javafx.stage.Stage;

import javax.sound.sampled.Clip;

public class InGameGUI {
    private static final int GAME_WIDTH = 1280;
    private static final int GAME_HEIGHT = 720;
    private static Player player;
    private static Monster monster;
    private static int selection;
    private static final double hpMAX = 440d;
    private static final double exMAX = 440d;
    private static Clip bgm;
    private static Clip battlebgm;
    private static boolean bgmRunning = true;
    public static Clip getbgm() {
        return bgm;
    }
    public static Clip getbattle() {
        return battlebgm;
    }
    public static void setBgmRunning(boolean val) {
        bgmRunning = val;
    }
    public static void updateInfo(Rectangle hp, Rectangle ex, Text level) {
        double hpFrac = (double)player.getHealth() / (double)player.getMaxHealth() * hpMAX;
        double exFrac = (double)player.getExp() / (double)player.getMaxExp() * exMAX;
        hp.setWidth(hpFrac);
        ex.setWidth(exFrac);
        level.setText(String.valueOf(player.getLevel()));
    }
    public static Player getPlayer() {
        return player;
    }
    public static Monster getMonster() {
        return monster;
    }
    public static Scene createScene() {
        if (!bgmRunning) {
            Transition.stop(battlebgm);
            bgm = Transition.makeRandClip("./music/menu/bgm/");
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
            Transition.play(bgm);
            bgmRunning = true;
        } else {
            bgm = TitleGUI.getClip();
        }
        // Initialize
        player = NameGUI.getPlayer();
        selection = 0;
        // Create window and background
        Group root = new Group();
        Image bgImage = new Image(InGameGUI.class.getResource("/images/ingamebg.png").toExternalForm());
        ImagePattern bgPattern = new ImagePattern(bgImage);
        Scene scene = new Scene(root, GAME_WIDTH, GAME_HEIGHT, bgPattern);
        // Player info background
        Image playerinfoImg = new Image(InGameGUI.class.getResource("/images/playerinfobg.png").toExternalForm());
        ImageView playerinfoImgView = new ImageView(playerinfoImg);
        playerinfoImgView.setX(318);
        playerinfoImgView.setY(30);
        root.getChildren().add(playerinfoImgView);
        // Player info labels
        String infoLabel = "NAME:\nLEVEL:\nHP:\nEX:";
        Text infoText = new Text(360, 100, infoLabel);
        infoText.setFont(Font.loadFont("file:fonts/SF Pixelate.ttf", 42));
        infoText.setFill(Color.GREEN);
        root.getChildren().add(infoText);
        // Player info name and level
        String name = player.getName();
        String level = String.valueOf(player.getLevel());
        Text nameText = new Text(515, 100, name);
        Text levelText = new Text(532, 139, level);
        nameText.setFont(Font.loadFont("file:fonts/SF Pixelate.ttf", 42));
        levelText.setFont(Font.loadFont("file:fonts/SF Pixelate.ttf", 42));
        nameText.setFill(Color.WHITE);
        levelText.setFill(Color.WHITE);
        root.getChildren().addAll(nameText, levelText);
        // Player info HP and EXP
        Rectangle hpBG = new Rectangle(450, 147, 450, 30);
        Rectangle exBG = new Rectangle(450, 187, 450, 30);
        hpBG.setFill(Color.WHITE);
        exBG.setFill(Color.WHITE);
        Rectangle hpFG = new Rectangle(455, 152, 440, 20);
        Rectangle exFG = new Rectangle(455, 192, 440, 20);
        hpFG.setFill(Color.RED);
        exFG.setFill(Color.BLUE);
        root.getChildren().addAll(hpBG, exBG, hpFG, exFG);
        updateInfo(hpFG, exFG, levelText);
        // In game menu
        Image ingamemenuImg = new Image(InGameGUI.class.getResource("/images/ingamemenu.png").toExternalForm());
        ImageView ingamemenuImgView = new ImageView(ingamemenuImg);
        ingamemenuImgView.setX(140);
        ingamemenuImgView.setY(400);
        root.getChildren().add(ingamemenuImgView);
        // Menu arrows
        Image menuarrowImg = new Image(InGameGUI.class.getResource("/images/ingamemenuarrow.png").toExternalForm());
        ImageView menuarrowImgView = new ImageView(menuarrowImg);
        menuarrowImgView.setX(200);
        menuarrowImgView.setY(305);
        root.getChildren().add(menuarrowImgView);
        // Handle menu selection
        EventHandler<KeyEvent> menuArrow = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                Clip enter = Transition.makeClip("./music/menu/sfx/click.wav");
                Clip move = Transition.makeClip("./music/menu/sfx/move.wav");
                Clip heal = Transition.makeClip("./music/menu/sfx/heal.wav");
                heal.setMicrosecondPosition(500000);
                if (e.getCode() == KeyCode.LEFT) {
                    Transition.play(move);
                    selection -= selection == 0 ? -2 : 1;
                }
                if (e.getCode() == KeyCode.RIGHT) {
                    Transition.play(move);
                    selection += selection == 2 ? -2 : 1;
                }
                if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                    switch (selection) {
                        case 0:
                            // SPAWN
                            Transition.stop(bgm);
                            bgmRunning = false;
                            battlebgm = Transition.makeRandClip("./music/battle/bgm/");
                            battlebgm.setMicrosecondPosition(2000000);
                            Transition.play(battlebgm);
                            int monsterMaxLevel = player.getLevel() + 1;
                            int monsterMinLevel = player.getLevel() <= 3 ? 1 : player.getLevel() - 3;
                            MonsterSpawner spawner = new MonsterSpawner();
                            monster = spawner.spawn(monsterMinLevel, monsterMaxLevel);
                            scene.removeEventFilter(KeyEvent.KEY_PRESSED, this);
                            Transition.stay(root, 100, 12, BattlerGUI.createScene());
                            System.out.println("Monster SPAWNED");
                            break;
                        case 1:
                            // HEAL
                            Transition.stop(bgm);
                            Transition.play(heal);
                            while (heal.getMicrosecondLength() != heal.getMicrosecondPosition()) {}
                            Transition.play(bgm);
                            player.heal(999999999);
                            updateInfo(hpFG, exFG, levelText);
                            System.out.println("HEALED");
                            break;
                        case 2:
                            // QUIT
                            Transition.play(enter);
                            scene.removeEventFilter(KeyEvent.KEY_PRESSED, this);
                            Transition.end(root, 1000, TitleGUI.createScene());
                    }
                }
                switch (selection) {
                    case 0:
                        menuarrowImgView.setX(200);
                        break;
                    case 1:
                        menuarrowImgView.setX(540);
                        break;
                    case 2:
                        menuarrowImgView.setX(880);
                        break;
                }
            }
        };
        scene.addEventFilter(KeyEvent.KEY_PRESSED, menuArrow);

        Transition.enter(root, 1000);
        return scene;
    }
}
