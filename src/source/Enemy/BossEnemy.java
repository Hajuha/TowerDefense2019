package sample;

import javafx.scene.canvas.GraphicsContext;

import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class BossEnemy extends Enemy {
    private final int cash_Boss = 300;

    private final int blood_first = 1000;
    private final int armor_boss = 2;
    private final int speed_boss = 1;
    private static final Image Boss_Img = new Image("file:src/res/Assets/Enemy/bos.png",
            60, 60, false, false);

    public BossEnemy(List<Point> pointList) {
        super();
        this.cash = cash_Boss;
        setFirst_Blood(blood_first);
        setSpeed(speed_boss);
        setArmor(armor_boss);
        this.image = Boss_Img;
        loadRoad(pointList);
        setPosition(roadList.get(0).getX(), roadList.get(0).getY());
        setDri(angle_Up);
        this.i = 0;
        angle = 90;
        getRoadList();
    }

    @Override
    public void ShowObject(GraphicsContext gc) {
    }

    @Override
    public void Render(GraphicsContext gc) {
        Move();
        gc.drawImage(image, this.x_pos, this.y_pos, 50, 50);
        gc.setFill(Color.GRAY);
        gc.fillRect(x_pos + 40 / 4, y_pos - 3, 70 / 2, 2);
        gc.setFill(Color.RED);
        gc.fillRect(x_pos + 40 / 4, y_pos - 3, 70 / 2 * getBlood() / blood_first, 2);
    }

    public void loadImage(String path) {
        this.image = new Image(path + ".png");
    }
}
