import javafx.application.Application;

import javafx.scene.Scene;

import javafx.stage.Stage;

public class GameGUI extends Application {
    private static Stage stage;
    public static void main(String[] args) {
        launch(args);
    }
    public static Stage getStage() {
        return stage;
    } 
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setTitle("CODE LAB BATTLER");
        Scene scene = TitleGUI.createScene();
        stage.setScene(scene);
        stage.show();
    }
}
