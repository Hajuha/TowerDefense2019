package sample;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.concurrent.Task;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

import javafx.animation.TranslateTransition;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.scene.input.KeyCode;
import javafx.scene.effect.DropShadow;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Start {

    private int width = 1200, height = 820;
    private static final Font FONT = Font.font("", FontWeight.BOLD, 18);
    private static Font font;
    private MenuBox menu;
    private String musicFile = "src/res/Audio/sound.mp3";     // For example
    private Media sound = new Media(new File(musicFile).toURI().toString());
    private MediaPlayer mediaPlayer = new MediaPlayer(sound);
    private Media gameSound = new Media(new File("src/res/Audio/stageSound.mp3").toURI().toString());
    private MediaPlayer gameMedia = new MediaPlayer(gameSound);
    private AudioClip clickMedia = new AudioClip(new File("src/res/Audio/clickSound.mp3").toURI().toString());

    public Start()
    {
        gameMedia.setOnEndOfMedia(new Runnable() {
        @Override
        public void run() {
            gameMedia.seek(Duration.ZERO);
        }
        });
       mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
    }

    private Scene createGame() throws FileNotFoundException, InterruptedException {
        sample.GameStage gameStage = new sample.GameStage();
        return gameStage.getMainScene();
    }

    private Scene createInstruction() throws IOException {
        StackPane root = new StackPane();
        InputStream is = Files.newInputStream(Paths.get("src/res/Assets/ins.jpg"));
        ImageView img = new ImageView(new Image(is));
        img.setFitWidth(width);
        img.setFitHeight(height);
        root.getChildren().add(img);
        Scene scene = new Scene(root);
        return scene;
    }

    private Scene createCredit() {
        StackPane root = new StackPane();
        root.setPrefSize(width, height);
        Rectangle bg = new Rectangle(width, height);
        bg.setOpacity(0.6);

        try (InputStream is = Files.newInputStream(Paths.get("src/res/Assets/bg2.jpg"));
             InputStream fontStream = Files.newInputStream(Paths.get("src/res/Assets/Font/cod_font.ttf"))) {
            ImageView img = new ImageView(new Image(is));
            img.setFitWidth(width);
            img.setFitHeight(height);
            root.getChildren().add(img);
            font = Font.loadFont(fontStream, 30);
        } catch (IOException e) {
            System.out.println("Couldn't load image or font");
        }
        Text credit = new Text("Tower Defense\n" +
                "by\n" +
                "Dai, Hao, Tiep\n" +
                "Source: https://github.com/Hajuha/TowerDefense2019");

        credit.setTextAlignment(TextAlignment.CENTER);
        credit.setFill(Color.WHITE);
        credit.setLineSpacing(2);
        credit.setFont(FONT);
        credit.setOpacity(1);

        root.getChildren().addAll(bg, credit);
        StackPane.setAlignment(credit, Pos.CENTER);
        Scene scene = new Scene(root);
        return scene;
    }


    public Stage createContent() throws IOException, InterruptedException {
        Stage createContent = new Stage();
        Scene creditsScene = createCredit();
        Scene instructionScene = createInstruction();
        gameMedia.setVolume(0.2);
        StackPane root = new StackPane();
        root.setPrefSize(width, height);

        try (InputStream is = Files.newInputStream(Paths.get("src/res/Assets/bg2.jpg"));
             InputStream fontStream = Files.newInputStream(Paths.get("src/res/Assets/Font/cod_font.ttf"))) {
            ImageView img = new ImageView(new Image(is));
            img.setFitWidth(width);
            img.setFitHeight(height);

            root.getChildren().add(img);

            font = Font.loadFont(fontStream, 30);
        } catch (IOException e) {
            System.out.println("Couldn't load image or font");
        }

        MenuItem itemQuit = new MenuItem("QUIT");
        itemQuit.setOnMouseClicked(event -> System.exit(0));
        MenuItem itemCredits = new MenuItem("CREDITS");
        itemCredits.setOnMouseClicked(e -> {
            clickMedia.play();
            createContent.setScene(creditsScene);

        });
        MenuItem itemInstruction = new MenuItem("INSTRUCTION");
        itemInstruction.setOnMouseClicked(e -> {
            clickMedia.play();
            createContent.setScene(instructionScene);

        });
        MenuItem itemStart = new MenuItem("NEW GAME");
        menu = new MenuBox("TOWER DEFENSE",
                itemStart,
                itemInstruction,
                itemCredits,
                itemQuit);

        root.getChildren().add(menu);
        root.setAlignment(Pos.CENTER);
        Scene startScene = new Scene(root);

        creditsScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                createContent.setScene(startScene);
            }
        });

        instructionScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                createContent.setScene(startScene);
            }
        });

        itemStart.setOnMouseClicked(e -> {
            clickMedia.play();
            menu.hide();
            mediaPlayer.stop();
            gameMedia.play();
            Scene gameScene = null;
            try {
                gameScene = createGame();
            } catch (FileNotFoundException | InterruptedException ex) {
                ex.printStackTrace();
            }
            createContent.setScene(gameScene);
            assert gameScene != null;
            gameScene.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ESCAPE) {
                    gameMedia.stop();
                    mediaPlayer.play();
                    menu.show();
                    createContent.setScene(startScene);
                }
            });

        });

        createContent.setScene(startScene);

        startScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                if (menu.isVisible()) {
                    menu.hide();
                } else {
                    menu.show();
                }

            }
        });
        mediaPlayer.play();
        createContent.setTitle("TowerDefense");
        return createContent;
    }

    public Stage getMainStage() throws IOException, InterruptedException {
        return createContent();
    }

    private static class MenuBox extends StackPane {
        public MenuBox(String title, MenuItem... items) {
            Rectangle bg = new Rectangle(400, 720);
            bg.setOpacity(0.5);

            DropShadow shadow = new DropShadow(7, 5, 0, Color.BLACK);
            shadow.setSpread(0.8);

            //bg.setEffect(shadow);

            Text text = new Text(title);
            text.setFont(font);
            text.setFill(Color.WHITE);

            Line hSep = new Line();
            hSep.setEndX(250);
            hSep.setStroke(Color.DARKGREEN);
            hSep.setOpacity(0.4);

            Line vSep = new Line();
            vSep.setStartX(300);
            vSep.setEndX(300);
            vSep.setEndY(600);
            vSep.setStroke(Color.DARKGREEN);
            vSep.setOpacity(0.4);

            VBox vb = new VBox();
            vb.getChildren().addAll(text);
            vb.setPadding(new Insets(100, 0, 0, 0));
            vb.setAlignment(Pos.TOP_CENTER);

            VBox vbox = new VBox();
            vbox.setAlignment(Pos.TOP_CENTER);
            vbox.setPadding(new Insets(300, 0, 0, 0));
            vbox.getChildren().addAll(items);

            System.out.print(vbox.getPrefHeight());
            vbox.setAlignment(Pos.TOP_CENTER);
            //setAlignment(Pos.CENTER);


            VBox endBox = new VBox();
            Text end = new Text("ver 1.0.0. Powered by JavaFX");

            end.setFill(Color.WHITE);
            end.setFont(Font.font("", FontWeight.LIGHT, 13));
            endBox.getChildren().addAll(end);
            endBox.setAlignment(Pos.BOTTOM_CENTER);
            endBox.setPrefSize(200, 50);
            //    endBox.setPadding( new Insets(0,0,0,100));

            getChildren().addAll(bg, vb, endBox, vbox);
        }

        public void show() {
            setVisible(true);

            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.3), this);

            tt.setToY(0);
            tt.play();
        }

        public void hide() {

            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.3), this);

            tt.setToY(-350);
            tt.setOnFinished(event -> setVisible(false));
            tt.play();
        }
    }

    private static class MenuItem extends StackPane {
        public MenuItem(String name) {
            Rectangle bg = new Rectangle(300, 24);

            LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop(0, Color.BLACK),
                    new Stop(0.2, Color.DARKGREY));

            bg.setFill(gradient);
            bg.setVisible(false);
            bg.setEffect(new DropShadow(5, 0, 5, Color.BLACK));

            Text text = new Text(name);
            text.setFill(Color.LIGHTGREY);
            text.setFont(font);
            getChildren().addAll(bg, text);
            setAlignment(Pos.CENTER);
            setOnMouseEntered(event -> {
                bg.setVisible(true);
                text.setFill(Color.WHITE);
            });

            setOnMouseExited(event -> {
                bg.setVisible(false);
                text.setFill(Color.LIGHTGREY);
            });

            setOnMousePressed(event -> {
                bg.setFill(Color.WHITE);
                text.setFill(Color.BLACK);
            });

            setOnMouseReleased(event -> {
                bg.setFill(gradient);
                text.setFill(Color.WHITE);
            });
        }
    }


}