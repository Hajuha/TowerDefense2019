package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        GameStage  gameStage = new GameStage();
        primaryStage = gameStage.getMainStage();
        Stage root = new Stage();
        Button startBtn = new Button("Start");
        VBox layout = new VBox();
        Stage finalPrimaryStage = primaryStage;
        startBtn.setOnAction(e -> finalPrimaryStage.show());
        layout.getChildren().addAll(startBtn);
        Scene startScene = new Scene(layout, 1200, 700);
        root.setScene(startScene);
        root.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
