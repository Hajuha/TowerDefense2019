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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import sample.Tower;
import sample.Enemy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// Man choi, dinh nghia trang thia bat dau cua game
public class GameStage {
    private final static int SCREEN_HEIGHT = 720;
    private final static int SCREEN_WIDTH = 1200;
    private final static int SCREEN_TITLEMAP = 30;
    private final static String GAME_TITLE = "Tower Defense";
    private final static int CASH_SNIPERTOWER = 50;
    private final static int CASH_NORMALTOWER = 70;
    private final static int CASH_MACHINEGUNTOWER = 100;
    private static int[][] MapTitle = new int[24][40];
    private static Image[][] imageMap = new Image[24][40];
    static List<GameTile> listBullet = new ArrayList<>();
    private List<Enemy> normalEnemyAction = new ArrayList<>();
    private List<Point> ListRoad = new ArrayList<>();
    private GraphicsContext mainGraphic;
    private Canvas mainCanvas;
    private Scene mainScene;
    private Stage mainStage;
    private Group root;
    ImageView iv; //vùng thao tác ảnh
    private SniperTower listTower = new SniperTower();
    private int cash = 500; // số tiền đang có
    private int bloodFull = 100; //số máu ban đầu của nhà chủ

    private Scanner input = new Scanner(new File("src/MapGame1.txt")); //ds tháp được đặt
    static  int i = 0;
    static  int j = 0;

    public GameStage() throws FileNotFoundException, InterruptedException {
        mainCanvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        mainGraphic = mainCanvas.getGraphicsContext2D();
        root = new Group();
        root.getChildren().add(mainCanvas);
        mainScene = new Scene(root);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        mainStage.setTitle(GAME_TITLE);

        Hbox_SniperTower hbox_sniperTower= new Hbox_SniperTower();
        Hbox_NormalTower hbox_normalTower= new Hbox_NormalTower();
        Hbox_MachineGunTower hbox_machineGunTower = new Hbox_MachineGunTower();
        root.getChildren().addAll( hbox_sniperTower.getHbox_Tower(), hbox_normalTower.getHbox_Tower(), hbox_machineGunTower.getHbox_Tower());

        List<Bullet> bulletAction = new ArrayList<>();
        LoadMap();
        Enemy ListEnemy = new NormalEnemy(ListRoad);
        Font theFont = Font.font( "Helvetica", FontWeight.BOLD, 24 );
        mainGraphic.setFont( theFont );
        mainGraphic.setFill( Color.GREEN );
        mainGraphic.setStroke( Color.BLACK );
        mainGraphic.setLineWidth(1);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    DrawMap();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                if(hbox_sniperTower.isIs_click() && cash >= CASH_SNIPERTOWER)
                {
                    System.out.println("Sniper");
                    hbox_sniperTower.setupGestureTarget(mainScene,MapTitle, mainGraphic);
                    hbox_sniperTower.Render_Hbox(mainGraphic);
                    if(hbox_sniperTower.isPut()) {
                        cash-=CASH_SNIPERTOWER;
                        listTower.towerList.add(new sample.SniperTower(hbox_sniperTower.getTower().x_pos, hbox_sniperTower.getTower().y_pos));
                        hbox_sniperTower.setPut(false);
                        hbox_sniperTower.setIs_click(false);
                    }
                    System.out.println(hbox_sniperTower.isIs_click());

                }
                else if(hbox_normalTower.isIs_click() && cash >= CASH_NORMALTOWER)
                {
                    System.out.println("Normal");
                    hbox_normalTower.setupGestureTarget(mainScene, MapTitle, mainGraphic);
                    hbox_normalTower.Render_Hbox(mainGraphic);
                    if(hbox_normalTower.isPut())
                    {
                        cash-=CASH_NORMALTOWER;
                        listTower.towerList.add(new NormalTower(hbox_normalTower
                                .getTower().x_pos, hbox_normalTower.getTower().y_pos));
                        hbox_normalTower.setPut(false);
                        hbox_normalTower.setIs_click(false);
                    }


                }
                else if(hbox_machineGunTower.isIs_click() && cash >= CASH_MACHINEGUNTOWER)
                {
                    System.out.println("Machine");
                    hbox_machineGunTower.setupGestureTarget(mainScene, MapTitle, mainGraphic);
                    hbox_machineGunTower.Render_Hbox(mainGraphic);
                    if(hbox_machineGunTower.isPut())
                    {
                        cash-=CASH_MACHINEGUNTOWER;
                        listTower.towerList.add(new sample.MachineGunTower(hbox_machineGunTower
                                .getTower().x_pos, hbox_machineGunTower.getTower().y_pos));
                        hbox_machineGunTower.setPut(false);
                        hbox_machineGunTower.setIs_click(false);
                    }
                    
                }

                if(!normalEnemyAction.isEmpty() && i == 0)  ListEnemy.adds(normalEnemyAction.remove(0));
                i = (i > 120) ? 0 : i + 1;
                ListEnemy.RenderList(mainGraphic);
                for(Tower t : listTower.towerList){
                    t.Render(mainGraphic, ListEnemy.getListEnemy());
                }
                cash += ListEnemy.cashIncrease;
                ListEnemy.cashIncrease = 0;
                bloodFull -= ListEnemy.bloodDecrease;
                ListEnemy.bloodDecrease = 0;
                String pointsText = "CASH: $" + (cash);
                mainGraphic.fillText( pointsText, SCREEN_WIDTH/2, SCREEN_TITLEMAP );
                mainGraphic.strokeText( pointsText, SCREEN_WIDTH/2, SCREEN_TITLEMAP );
                String bloodText = "Blood: " + (bloodFull);
                mainGraphic.fillText( bloodText, SCREEN_WIDTH*3/4, SCREEN_TITLEMAP );
                mainGraphic.strokeText( bloodText, SCREEN_WIDTH*3/4, SCREEN_TITLEMAP );
            }
        };

        timer.start();


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

        Image tilemap0 = new Image("file:src/Assets/Map/Map-" + 0 + ".png",
                30, 30, true, true);
        Image tilemap1 = new Image("file:src/Assets/Map/Map-" + 1 + ".png",
                30, 30, true, true);
        Image tilemap2 = new Image("file:src/Assets/Map/Map-" + 2 + ".png",
                30, 30, true, true);
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 40; j++) {
                int a = input.nextInt();
                MapTitle[i][j] = a;
            }
            //System.out.println();
        }
        int index  =0;
        int maxRoad = input.nextInt();
        while ( index < maxRoad)
        {
            int x = input.nextInt();
            int y = input.nextInt();
            //System.out.println(x + " " + y);
            ListRoad.add(new Point(x, y));
            index ++;
        }
        int maxEnemy = input.nextInt();
        index = 0;
        while (index < maxEnemy)
        {
            index ++;
            int enemy = input.nextInt();
            switch (enemy)
            {
                case 0 :
                    normalEnemyAction.add(new SmallerEnemy(ListRoad));
                    break;
                case 1 :
                    normalEnemyAction.add(new NormalEnemy(ListRoad));
                    break;
                case 2 :
                    normalEnemyAction.add(new TankerEnemy(ListRoad));
                    break;
                case 3 :
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


    public List<Point> getListRoad() {
        return ListRoad;
    }
}
