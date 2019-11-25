package sample;

import HboxTower.HBoxTower;
import HboxTower.Hbox_MachineGunTower;
import HboxTower.Hbox_NormalTower;
import HboxTower.Hbox_SniperTower;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import source.Main.MapGame;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

// Man choi, dinh nghia trang thia bat dau cua game
public class GameStage {
    private final static int SCREEN_HEIGHT = 820;
    private final static int SCREEN_WIDTH = 1200;
    private final static int SCREEN_TITLEMAP = 30;
    private final static int CASH_SNIPERTOWER = 100;
    private final static int CASH_NORMALTOWER = 300;
    private final static int CASH_MACHINEGUNTOWER = 600;
    private final int FirstCash = 300;
    private final int FirstBlood = 20;

    private final static String GAME_TITLE = "Tower Defense";
    private final Image topbar = new Image("file:src/res/Assets/Map/Topbar.png");
    private final Image bg = new Image("file:src/res/Assets/bg2.jpg", 1200, 700, false, false);
    private final Image victory = new Image("file:src/res/Assets/Victory.png");
    private final Image defeat = new Image("file:src/res/Assets/Defeat.png");

    ImageView iv;
    private MapGame mapGame = new MapGame() ;
    private static int[][] MapTitle = new int[24][40];
    private static Image[][] imageMap = new Image[24][40];
    private List<Enemy> normalEnemyAction = new ArrayList<>();
    private List<Point> ListRoad = new ArrayList<>();
    private SniperTower listTower = new SniperTower();
    private Enemy ListEnemy;

    private GraphicsContext mainGraphic;
    private Canvas mainCanvas;
    private Scene mainScene;
    private Scene winnerScene;
    private Stage mainStage;
    private Group root;

    private int i;
    private int j;
    private int level = 1;
    private int cash;
    private int bloodFull;
    private int FirstWave;
    private int wave;
    private static Font theFont;

    private int index;

    private Hbox_SniperTower hbox_sniperTower = new Hbox_SniperTower();
    private Hbox_NormalTower hbox_normalTower = new Hbox_NormalTower();
    private Hbox_MachineGunTower hbox_machineGunTower = new Hbox_MachineGunTower();


    public GameStage() throws FileNotFoundException, InterruptedException {
        mainCanvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        mainGraphic = mainCanvas.getGraphicsContext2D();
        root = new Group();
        root.getChildren().add(mainCanvas);
        mainScene = new Scene(root);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        mainStage.setTitle(GAME_TITLE);

        root.getChildren().addAll(hbox_sniperTower.getHbox_Tower(), hbox_normalTower.getHbox_Tower(), hbox_machineGunTower.getHbox_Tower());

        hbox_machineGunTower.setupGestureTarget(mainScene, mapGame.getMapTitle(), mainGraphic);

        setFirstGame();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (isWin()) {

                    RenderNext();
                } else if (isLose()) {
                    RenderReplay();

                }
                else {
                    mapGame.DrawMap(mainGraphic);
                    PutTower();

                    if (!normalEnemyAction.isEmpty() && i == 0) {
                        ListEnemy.adds(normalEnemyAction.remove(0));
                        wave--;
                    }

                    i = (i > 120) ? 0 : i + 1;
                    ListEnemy.RenderList(mainGraphic);
                    for (Tower t : listTower.towerList) {
                        t.Render(mainGraphic, ListEnemy.getListEnemy());
                    }

                    update();
                }

            }
        };
        timer.start();
    }
    public Stage getMainStage() { return mainStage; }


    public Scene getMainScene() { return mainScene; }


    public void ResetGame() {
        normalEnemyAction = new ArrayList<>();
        ListRoad = new ArrayList<>();
        listTower = new SniperTower();
        try {
            setFirstGame();
        }
        catch (Exception e) { }
    }
    public boolean isWin() {
        return (ListEnemy.getListEnemy().isEmpty() && normalEnemyAction.isEmpty() && bloodFull > 0);
    }
    public boolean isLose() {
        return (bloodFull <= 0);
    }
    public void NextGame() {
        switch (level) {
            case 3:
                Scene winnerScene = createWin();
                mainStage.setScene(winnerScene);
                System.out.println("win");
                break;
            default:
                level++;
                ResetGame();
                break;
        }

    }

    public void NewGame() {
        level = 1;
        ResetGame();
    }

    public void setFirstGame() throws FileNotFoundException {
        i = 0;
        j = 0;
        index = 0;
        cash = FirstCash;
        bloodFull = FirstBlood;
        mapGame.LoadMap(level);
        FirstWave = mapGame.getWave();
        wave = FirstWave;
        normalEnemyAction = mapGame.getEnemies();
        ListEnemy = new sample.NormalEnemy();
        normalEnemyAction = mapGame.getEnemies();
    }
    public void RenderNext()
    {
        index ++;
        mainGraphic.drawImage(bg, 0, 100);
        mainGraphic.drawImage(victory, 600 - victory.getWidth() / 2, 450 - victory.getHeight() / 2);
        RenderOut();
        if (index == 500 && level <= 3)
        {
            NextGame();
            index = 0;
        }
    }
    public void RenderReplay()
    {
        index++;
        mainGraphic.drawImage(bg, 0, 100);
        mainGraphic.drawImage(defeat, 600 - defeat.getWidth() / 2, 450 - defeat.getHeight() / 2);
        RenderOut();
        if (index == 500)
        {
            ResetGame();
            index = 0;
        }
    }
    public void RenderOut()
    {
        index++;
        mainGraphic.setStroke(Color.RED);
        mainGraphic.strokeRect(400, 600, 400, 8);
        mainGraphic.setFill(Color.WHITE);
        mainGraphic.fillRect(401, 601, 398 * index / 500, 5);
        mainGraphic.setFont(Font.font("src/Assets/Font/cod_font.ttf", 15));
        String levelText = "Loading...  " + index / 5 + "%";
        mainGraphic.fillText(levelText, 550, 650);
    }
    public void update()
    {
        cash += ListEnemy.cashIncrease;
        ListEnemy.cashIncrease = 0;
        bloodFull -= ListEnemy.bloodDecrease;
        ListEnemy.bloodDecrease = 0;
        mainGraphic.drawImage(topbar, 0, 0);
        String pointsText = "$" + (cash);
        mainGraphic.setFill(Color.YELLOW);
        mainGraphic.setFont(Font.font("src/Assets/Font/cod_font.ttf", 30));
        mainGraphic.fillText(pointsText, 600, 50);
        mainGraphic.strokeText(pointsText, SCREEN_WIDTH / 2, 50);
        String bloodText = "\uD83D\uDDA4" + (bloodFull);
        mainGraphic.fillText(bloodText, 800, 50);
        mainGraphic.strokeText(bloodText, 800, 50);
        String levelText = "Level : " + level;
        mainGraphic.fillText(levelText, 1000, 50);
        mainGraphic.strokeText(levelText, 1000, 50);
        String WaveText = "Wave : " + wave + "/" + FirstWave;
        mainGraphic.fillText(WaveText, 0, 50);
        mainGraphic.strokeText(WaveText, 0, 50);
    }
    public void PutTower()
    {
        switch (hbox_machineGunTower.getIndex_tower()) {
            case 1:
                hbox_machineGunTower.Render_Hbox(mainGraphic, hbox_sniperTower.getTower().getRange());
                if (hbox_machineGunTower.isPut() && cash >= CASH_SNIPERTOWER) {
                    cash -= CASH_SNIPERTOWER;
                    listTower.towerList.add(new sample.SniperTower(hbox_machineGunTower.getTower().x_pos, hbox_machineGunTower.getTower().y_pos - 20));
                }
                hbox_machineGunTower.setPut(false);
                break;
            case 2:
                hbox_machineGunTower.Render_Hbox(mainGraphic, hbox_normalTower.getTower().getRange());
                if (hbox_machineGunTower.isPut() && cash >= CASH_NORMALTOWER) {
                    cash -= CASH_NORMALTOWER;
                    listTower.towerList.add(new sample.NormalTower(hbox_machineGunTower.getTower().x_pos, hbox_machineGunTower.getTower().y_pos - 20));
                }
                hbox_machineGunTower.setPut(false);
                break;
            case 3:
                hbox_machineGunTower.Render_Hbox(mainGraphic, hbox_machineGunTower.getTower().getRange());
                if (hbox_machineGunTower.isPut() && cash >= CASH_MACHINEGUNTOWER) {
                    cash -= CASH_MACHINEGUNTOWER;
                    listTower.towerList.add(new sample.MachineGunTower(hbox_machineGunTower.getTower().x_pos, hbox_machineGunTower.getTower().y_pos - 20));
                }
                hbox_machineGunTower.setPut(false);
                break;
        }
    }
}
