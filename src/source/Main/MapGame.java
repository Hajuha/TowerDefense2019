package source.Main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Polyline;
import sample.Enemy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class MapGame  {
    private final Image tilemap0 = new Image("file:src/res/Assets/Map/Map-" + 0 + ".png",
            30, 30, true, true);
    private final Image tilemap1 = new Image("file:src/res/Assets/Map/Map-" + 1 + ".png",
            30, 30, true, true);
    private final Image tilemap2 = new Image("file:src/res/Assets/Map/Map-" + 2 + ".png",
            30, 30, true, true);
    private final static int SCREEN_HEIGHT = 820;
    private final static int SCREEN_WIDTH = 1200;
    private final Image topbar = new Image("file:src/res/Assets/Map/Topbar.png");

    private int[][] MapTitle = new int[24][40];
    private Image[][] imageMap = new Image[24][40];
    private List<Enemy> enemies;
    private Road road;
    private int Wave;
    public MapGame(){
        super();
        road = new Road();
        enemies = new ArrayList<>();
    };
    public void DrawMap(GraphicsContext mainGraphic){
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
        road  = new Road();
        Scanner input = new Scanner(new File("src/TileMap/MapGame" + level + ".txt"));
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 40; j++) {
                int a = input.nextInt();
                MapTitle[i][j] = a;
            }
        }
        int index = 0;
        int maxRoad = input.nextInt();
        while (index < maxRoad) {
            int x = input.nextInt();
            int y = input.nextInt();
            road.add(new sample.Point(x, y));
            index++;
        }
        int maxEnemy = input.nextInt();
        Wave = maxEnemy;
        index = 0;
        while (index < maxEnemy) {
            index++;
            int enemy = input.nextInt();
            switch (enemy) {
                case 0:
                    enemies.add(new sample.SmallerEnemy(road.getRoadList()));
                    break;
                case 1:
                    enemies.add(new sample.NormalEnemy(road.getRoadList()));
                    break;
                case 2:
                    enemies.add(new sample.TankerEnemy(road.getRoadList()));
                    break;
                case 3:
                    enemies.add(new sample.BossEnemy(road.getRoadList()));
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

    public int[][] getMapTitle() {
        return MapTitle;
    }

    public int getWave() {
        return Wave;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
