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

public class BattlerGUI {
    private static final int GAME_WIDTH = 1280;
    private static final int GAME_HEIGHT = 720;

    private static Player player;
    private static final double hpMAX = 440d;
    private static final double exMAX = 440d;
    private static Group root;
    private static Text levelText;
    private static Rectangle playerHPFG;
    private static Rectangle exFG;

    private static Monster monster;
    private static Rectangle monsterHPFG;

    private static int selection;
    private static String turn;
    private static boolean monsterDeath;

    public static void initPlayerInfo() {
        // Player info background
        Image playerinfoImg = new Image(BattlerGUI.class.getResource("/images/playerinfobg.png").toExternalForm());
        ImageView playerinfoImgView = new ImageView(playerinfoImg);
        double x = 30;
        double y = 450;
        playerinfoImgView.setX(x);
        playerinfoImgView.setY(y);
        root.getChildren().add(playerinfoImgView);
        // Player info labels
        String infoLabel = "NAME:\nLEVEL:\nHP:\nEX:";
        Text infoText = new Text(x + 42, y + 70, infoLabel);
        infoText.setFont(Font.loadFont("file:fonts/SF Pixelate.ttf", 42));
        infoText.setFill(Color.GREEN);
        root.getChildren().add(infoText);
        // Player info name and level
        String name = player.getName();
        String level = String.valueOf(player.getLevel());
        Text nameText = new Text(x + 197, y + 70, name);
        levelText = new Text(x + 214, y + 109, level);
        nameText.setFont(Font.loadFont("file:fonts/SF Pixelate.ttf", 42));
        levelText.setFont(Font.loadFont("file:fonts/SF Pixelate.ttf", 42));
        nameText.setFill(Color.WHITE);
        levelText.setFill(Color.WHITE);
        root.getChildren().addAll(nameText, levelText);
        // Player info HP and EXP
        Rectangle hpBG = new Rectangle(x + 132, y + 117, 450, 30);
        Rectangle exBG = new Rectangle(x + 132, y + 157, 450, 30);
        hpBG.setFill(Color.WHITE);
        exBG.setFill(Color.WHITE);
        playerHPFG = new Rectangle(x + 137, y + 122, 440, 20);
        exFG = new Rectangle(x + 137, y + 162, 440, 20);
        playerHPFG.setFill(Color.RED);
        exFG.setFill(Color.BLUE);
        root.getChildren().addAll(hpBG, exBG, playerHPFG, exFG);
        updatePlayerInfo();
    }
    public static void updatePlayerInfo() {
        double hpFrac = (double)player.getHealth() / (double)player.getMaxHealth() * hpMAX;
        double exFrac = (double)player.getExp() / (double)player.getMaxExp() * exMAX;
        playerHPFG.setWidth(hpFrac);
        exFG.setWidth(exFrac);
        levelText.setText(String.valueOf(player.getLevel()));
        if (player.getHealth() <= 0) {
            InGameGUI.setBgmRunning(true);
            Transition.end(root, 1000, TitleGUI.createScene());
        }
    }
    public static void initMonsterInfo() {
        // Initialize monster
        monster = InGameGUI.getMonster();
        // Monster info background
        Image monsterinfoImg = new Image(BattlerGUI.class.getResource("/images/monsterinfobg.png").toExternalForm());
        ImageView monsterinfoImgView = new ImageView(monsterinfoImg);
        monsterinfoImgView.setFitHeight(200);
        double x = 600;
        double y = 30;
        monsterinfoImgView.setX(x);
        monsterinfoImgView.setY(y);
        root.getChildren().add(monsterinfoImgView);
        // Monster info labels
        String infoLabel = "NAME:\nLEVEL:\nHP:";
        Text infoText = new Text(x + 42, y + 70, infoLabel);
        infoText.setFont(Font.loadFont("file:fonts/SF Pixelate.ttf", 42));
        infoText.setFill(Color.RED);
        root.getChildren().add(infoText);
        // Monster info name and level
        String name = monster.getName();
        String level = String.valueOf(monster.getLevel());
        Text nameText = new Text(x + 197, y + 70, name);
        Text monsterLevelText = new Text(x + 214, y + 109, level);
        nameText.setFont(Font.loadFont("file:fonts/SF Pixelate.ttf", 42));
        monsterLevelText.setFont(Font.loadFont("file:fonts/SF Pixelate.ttf", 42));
        nameText.setFill(Color.WHITE);
        monsterLevelText.setFill(Color.WHITE);
        root.getChildren().addAll(nameText, monsterLevelText);
        // Monster info HP and EXP
        Rectangle hpBG = new Rectangle(x + 132, y + 117, 450, 30);
        hpBG.setFill(Color.WHITE);
        monsterHPFG = new Rectangle(x + 137, y + 122, 440, 20);
        monsterHPFG.setFill(Color.RED);
        root.getChildren().addAll(hpBG, monsterHPFG);
        updateMonsterInfo();
    }
    public static void updateMonsterInfo() {
        double hpFrac = (double)monster.getHealth() / (double)monster.getMaxHealth() * hpMAX;
        monsterHPFG.setWidth(hpFrac);
        if (monster.getHealth() <= 0) {
            int expGain = monster.getExpValue();
            player.gainExp(expGain);
            monsterDeath = true;
            Transition.end(root, 1000, InGameGUI.createScene());
        }
    }
    public static void battle() {
        if (turn == "player") {
            Clip atk = Transition.makeRandClip("./music/battle/sfx/");
            Transition.play(atk);
            int playerAttack = player.rollAttack();
            monster.damage(playerAttack);
            Transition.stay(root, 50, 2);
            updateMonsterInfo();
            turn = "monster";
            if (!monsterDeath) {
                battle();
            }
        } else {
            int monsterAttack = monster.rollAttack();
            player.damage(monsterAttack);
            Transition.stay(root, 50, 2);
            updatePlayerInfo();
            turn = "player";
        }
    }
    public static Scene createScene() {
        // Initialize player
        player = InGameGUI.getPlayer();
        // Initialize turn
        turn = "player";
        // Initialize selection
        selection = 0;
        // Initialize monster death
        monsterDeath = false;
        // Create window and background
        root = new Group();
        Image bgImage = new Image(BattlerGUI.class.getResource("/images/battlebg0.png").toExternalForm());
        ImagePattern bgPattern = new ImagePattern(bgImage);
        Scene scene = new Scene(root, GAME_WIDTH, GAME_HEIGHT, bgPattern);
        // Player info
        initPlayerInfo();
        updatePlayerInfo();
        // Monster info
        initMonsterInfo();
        updateMonsterInfo();
        // Battle menu
        Image battlemenuImg = new Image(BattlerGUI.class.getResource("/images/battlemenu.png").toExternalForm());
        Rectangle battlemenubg = new Rectangle(0, 260, GAME_WIDTH, 180);
        battlemenubg.setFill(Color.GRAY);
        battlemenubg.setOpacity(0.7);
        ImageView battlemenuImgView = new ImageView(battlemenuImg);
        battlemenuImgView.setX(0);
        battlemenuImgView.setY(270);
        root.getChildren().addAll(battlemenubg, battlemenuImgView);
        // Battle menu arrow
        Image battlemenuarrowImg = new Image(BattlerGUI.class.getResource("/images/battlemenuarrow.png").toExternalForm());
        ImageView battlemenuarrowImgView = new ImageView(battlemenuarrowImg);
        battlemenuarrowImgView.setX(145);
        battlemenuarrowImgView.setY(315);
        root.getChildren().add(battlemenuarrowImgView);
        // Handle menu arrow
        EventHandler<KeyEvent> menuArrow = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.RIGHT) {
                    Clip menu = Transition.makeClip("./music/menu/sfx/move.wav");
                    Transition.play(menu);
                    selection = selection == 0 ? 1 : 0;
                    battlemenuarrowImgView.setX(selection == 0 ? 145 : 735);
                }
                if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                    if (selection == 0) {
                        Clip click = Transition.makeClip("./music/menu/sfx/click.wav");
                        Transition.play(click);
                        battle();
                    } else {
                        Clip flee = Transition.makeClip("./music/battle/run.wav");
                        Transition.play(flee);
                        scene.removeEventFilter(KeyEvent.KEY_PRESSED, this);
                        Transition.end(root, 1000, InGameGUI.createScene());
                    }
                }
            }
        };
        scene.addEventFilter(KeyEvent.KEY_PRESSED, menuArrow);

        Transition.enter(root, 1000);
        return scene;
    }
}
