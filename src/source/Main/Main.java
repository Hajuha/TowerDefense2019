package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.Start;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Start gameStage = new Start();
        primaryStage = gameStage.getMainStage();
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}