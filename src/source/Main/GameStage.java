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
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import sample.Tower;
import sample.Enemy;
import source.Main.Media;

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
    private final static String GAME_TITLE = "Tower Defense";
    private final static int CASH_SNIPERTOWER = 50;
    private final static int CASH_NORMALTOWER = 150;
    private final static int CASH_MACHINEGUNTOWER = 400;
    private final int FirstCash = 300;
    private static int[][] MapTitle = new int[24][40];
    private static Image[][] imageMap = new Image[24][40];
    private List<Enemy> normalEnemyAction = new ArrayList<>();
    private List<Point> ListRoad = new ArrayList<>();
    private GraphicsContext mainGraphic;
    private Canvas mainCanvas;
    private Scene mainScene;
    private Scene winnerScene;
    private Stage mainStage;
    private Group root;
    ImageView iv; //vùng thao tác ảnh
    private SniperTower listTower = new SniperTower();
    private int cash;// số tiền đang có
    private int bloodFull;//số máu ban đầu của nhà chủ
    private Enemy ListEnemy;
    private final Image topbar = new Image("file:src/res/Assets/Map/Topbar.png");
    private int FirstWave;
    private int wave;
    private int i;
    private int j;
    private int level = 1;
    private static Font theFont;
    private AudioClip clickSound;
    private Scene createWin() {
        StackPane winner = new StackPane();
        Rectangle bg = new Rectangle(1200, 820);
        bg.setOpacity(0.6);
        bg.setStyle(String.valueOf(Color.RED));
        winner.getChildren().addAll(bg);
        winnerScene = new Scene(winner);
        return winnerScene;
    }

    public GameStage() throws FileNotFoundException, InterruptedException {
        mainCanvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        mainGraphic = mainCanvas.getGraphicsContext2D();
        root = new Group();
        root.getChildren().add(mainCanvas);
        mainScene = new Scene(root);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        mainStage.setTitle(GAME_TITLE);

        Hbox_SniperTower hbox_sniperTower = new Hbox_SniperTower();
        Hbox_NormalTower hbox_normalTower = new Hbox_NormalTower();
        Hbox_MachineGunTower hbox_machineGunTower = new Hbox_MachineGunTower();
        root.getChildren().addAll(hbox_sniperTower.getHbox_Tower(), hbox_normalTower.getHbox_Tower(), hbox_machineGunTower.getHbox_Tower());
        hbox_machineGunTower.setupGestureTarget(mainScene, MapTitle, mainGraphic);

        try (InputStream fontStream = Files.newInputStream(Paths.get("src/res/Assets/Font/retro.ttf"))) {
            theFont = Font.loadFont(fontStream, 30);
        } catch (IOException e) {
            System.out.println("Couldn't load image or font");
        }

        mainGraphic.setFont(theFont);
        mainGraphic.setStroke(Color.BLACK);
        mainGraphic.setLineWidth(1);

        cash = FirstCash;
        bloodFull = 20;
        i = 0;
        j = 0;
        LoadMap(level);
        ListEnemy = new NormalEnemy(ListRoad);
        mainScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {
                if (level == 3) {
                    //ListEnemy.clear();
                    Scene winnerScene = createWin();
                    mainStage.setScene(winnerScene);
                } else
                    NextGame();
            }
        });
        Media sound = new Media();
        clickSound = sound.getClickMedia();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (isWin()) {
                    NextGame();
                }
                if (isLose()) {
                    ResetGame();
                }

                try {
                    DrawMap();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                switch (hbox_machineGunTower.getIndex_tower()) {
                    case 1:
                        ((HBoxTower) hbox_machineGunTower).Render_Hbox(mainGraphic, hbox_sniperTower.getTower().getRange());
                        if (hbox_machineGunTower.isPut() && cash >= CASH_SNIPERTOWER) {
                            clickSound.play();
                            cash -= CASH_SNIPERTOWER;
                            listTower.towerList.add(new sample.SniperTower(hbox_machineGunTower.getTower().x_pos, hbox_machineGunTower.getTower().y_pos - 20));
                        }
                        hbox_machineGunTower.setPut(false);
                        break;
                    case 2:
                        ((HBoxTower) hbox_machineGunTower).Render_Hbox(mainGraphic, hbox_normalTower.getTower().getRange());
                        if (hbox_machineGunTower.isPut() && cash >= CASH_NORMALTOWER) {
                            clickSound.play();
                            cash -= CASH_NORMALTOWER;
                            listTower.towerList.add(new sample.NormalTower(hbox_machineGunTower.getTower().x_pos, hbox_machineGunTower.getTower().y_pos - 20));
                        }
                        hbox_machineGunTower.setPut(false);
                        break;
                    case 3:
                        ((HBoxTower) hbox_machineGunTower).Render_Hbox(mainGraphic, hbox_machineGunTower.getTower().getRange());
                        if (hbox_machineGunTower.isPut() && cash >= CASH_MACHINEGUNTOWER) {
                            clickSound.play();
                            cash -= CASH_MACHINEGUNTOWER;
                            listTower.towerList.add(new sample.MachineGunTower(hbox_machineGunTower.getTower().x_pos, hbox_machineGunTower.getTower().y_pos - 20));
                        }
                        hbox_machineGunTower.setPut(false);
                        break;
                }

                if (!normalEnemyAction.isEmpty() && i == 0) {
                    ListEnemy.adds(normalEnemyAction.remove(0));
                    wave--;

                }
                i = (i > 120) ? 0 : i + 1;
                ListEnemy.RenderList(mainGraphic);
                for (Tower t : listTower.towerList) {
                    t.Render(mainGraphic, ListEnemy.getListEnemy());
                }
                cash += ListEnemy.cashIncrease;
                ListEnemy.cashIncrease = 0;
                bloodFull -= ListEnemy.bloodDecrease;
                ListEnemy.bloodDecrease = 0;

                mainGraphic.drawImage(topbar, 0, 0);

                String pointsText = "$" + (cash);
                mainGraphic.setFill(Color.ORANGE);
                mainGraphic.fillText(pointsText, 600, 60);
                mainGraphic.strokeText(pointsText, 600, 60);
                String bloodText = "♥" + (bloodFull);
                mainGraphic.fillText(bloodText, 750, 60);
                mainGraphic.strokeText(bloodText, 750, 60);
                String levelText = "Level " + level;
                mainGraphic.fillText(levelText, 900, 60);
                mainGraphic.strokeText(levelText, 900, 60);
                String WaveText = "Wave:" + wave;
                mainGraphic.fillText(WaveText, 20, 60);
                mainGraphic.strokeText(WaveText, 20, 60);


            }
        };

        timer.start();


    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void DrawMap() throws FileNotFoundException {

        mainGraphic.drawImage(topbar, 0, 0);
        int x_pos = 0;
        int y_pos = 100;
        int width = SCREEN_WIDTH / 40;
        int height = (SCREEN_HEIGHT - 100) / 24;

        for (int i = 0; i < 24; i++) {
            x_pos = 0;
            for (int j = 0; j < 40; j++) {
                mainGraphic.drawImage(imageMap[i][j], x_pos, y_pos);
                x_pos += width;
            }
            y_pos += height;
        }
    }

    public void LoadMap(int level) throws FileNotFoundException {
        Scanner input = new Scanner(new File("src/TileMap/MapGame" + level + ".txt")); //ds tháp được đặt
        Image tilemap0 = new Image("file:src/res/Assets/Map/Map-" + 0 + ".png",
                30, 30, true, true);
        Image tilemap1 = new Image("file:src/res/Assets/Map/Map-" + 1 + ".png",
                30, 30, true, true);
        Image tilemap2 = new Image("file:src/res/Assets/Map/Map-" + 2 + ".png",
                30, 30, true, true);
        Image tilemap3 = new Image("file:src/res/Assets/Map/Map-" + 3 + ".png",
                30, 30, true, true);
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 40; j++) {
                int a = input.nextInt();
                MapTitle[i][j] = a;
            }
            //System.out.println();
        }
        int index = 0;
        int maxRoad = input.nextInt();
        while (index < maxRoad) {
            int x = input.nextInt();
            int y = input.nextInt();
            //System.out.println(x + " " + y);
            ListRoad.add(new Point(x, y));
            index++;
        }
        int maxEnemy = input.nextInt();
        FirstWave = maxEnemy;
        wave = FirstWave;
        index = 0;
        while (index < maxEnemy) {
            index++;
            int enemy = input.nextInt();
            switch (enemy) {
                case 0:
                    normalEnemyAction.add(new SmallerEnemy(ListRoad));
                    break;
                case 1:
                    normalEnemyAction.add(new NormalEnemy(ListRoad));
                    break;
                case 2:
                    normalEnemyAction.add(new TankerEnemy(ListRoad));
                    break;
                case 3:
                    normalEnemyAction.add(new BossEnemy(ListRoad));
                    break;
            }
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
                    case 3:
                        imageMap[i][j] = tilemap3;
                        break;
                }
            }
        }
    }

    public Scene getMainScene() {
        return mainScene;
    }

    public Group getRoot() {
        return root;
    }

    public void ResetGame() {
        normalEnemyAction = new ArrayList<>();
        ListRoad = new ArrayList<>();
        listTower = new SniperTower();
        cash = FirstCash;
        bloodFull = 100;
        i = 0;
        j = 0;
        try {
            LoadMap(level);
        } catch (Exception e) {
        }
        ListEnemy = new sample.NormalEnemy(ListRoad);
        this.bloodFull = 20;
        this.cash = FirstCash + (level - 1) * 30;
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

    public List<Point> getListRoad() {
        return ListRoad;
    }
}