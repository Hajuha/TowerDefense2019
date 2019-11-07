package sample;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

// Man choi, dinh nghia trang thia bat dau cua game
public class GameStage {
    private final static int SCREEN_HEIGHT = 720;
    private final static int SCREEN_WIDTH = 1200;
    private final static String GAME_TITLE = "Tower Defense";
    final static Image imageBullet = new Image("Bullet.png", 30, 30, true, true);
    static int[][] MapTitle = new int[24][40];
    static Image[][] imageMap = new Image[24][40];
    static List<GameTile> listBullet = new ArrayList<>();
    GraphicsContext mainGraphic;
    private Canvas mainCanvas;
    private Scene mainScene;
    private Stage mainStage;
    private Group root;

    public GameStage() throws FileNotFoundException {
        mainCanvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        mainGraphic = mainCanvas.getGraphicsContext2D();
        root = new Group();
        root.getChildren().add(mainCanvas);
        mainScene = new Scene(root);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        mainStage.setTitle(GAME_TITLE);
        NormalEnemy normalEnemy = new NormalEnemy();
        Bullet bullet = new Bullet(imageBullet);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    DrawMap();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

//                GameTile bullet = new Bullet(((NormalEnemy) normalEnemy).getPosition(), imageBullet);
//                listBullet.add(bullet);
//                for (int i = 0; i < listBullet.size(); i ++)
//                {
//                    listBullet.get(i).Render(mainGraphic);
//                }

                normalEnemy.Render(mainGraphic);
                bullet.setDestination(normalEnemy.getPosition());
                bullet.Render(mainGraphic);
            }

        };
        timer.start();
        LoadMap();
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void DrawMap() throws FileNotFoundException {

        int x_pos = 0;
        int y_pos = 0;
        int width = SCREEN_WIDTH / 40;
        int height = SCREEN_HEIGHT / 24;

        for (int i = 0; i < 24; i++) {

            x_pos = 0;

            for (int j = 0; j < 40; j++) {
                mainGraphic.drawImage(imageMap[i][j], x_pos, y_pos);
                x_pos += width;
            }
            y_pos += height;
        }
    }

    public void LoadMap() throws FileNotFoundException {
        Scanner input = new Scanner(new File("src/MapGame1.txt"));
        Image tilemap0 = new Image("file:src/MapGameImage/MapGame" + 0 + ".png",
                30, 30, true, true);
        Image tilemap1 = new Image("file:src/MapGameImage/MapGame" + 1 + ".png",
                30, 30, true, true);
        Image tilemap2 = new Image("file:src/MapGameImage/MapGame" + 2 + ".png",
                30, 30, true, true);
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 40; j++) {
                int a = input.nextInt();
                MapTitle[i][j] = a;
            }
            System.out.println();
        }
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 40; j++) {
                switch (MapTitle[i][j]) {
                    case 0:
                        imageMap[i][j] = tilemap0;
                        break;
                    case 1:
                        imageMap[i][j] = tilemap1;
                        break;
                    case 2:
                        imageMap[i][j] = tilemap2;
                        break;
                }

            }
        }

    }
}
