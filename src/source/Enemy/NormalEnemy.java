package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.List;
import sample.Enemy;
import sample.Point;


public class NormalEnemy extends Enemy {
    private static final Image Normal_Img = new Image("file:src/res/Assets/Enemy/NormalEnemy.png",
            50, 50, false, false);
    private final int cash_Normal = 110;
    private static final int blood_first = 150;
    private static final int armor_normal = 2;
    private static final int speed = 1;

    public NormalEnemy(){};
    public NormalEnemy(List<Point> pointList) {
        super();
        this.cash = cash_Normal;
        setFirst_Blood(blood_first);
        setSpeed(speed);
        setArmor(armor_normal);
        loadRoad(pointList);
        this.image = Normal_Img;
        setDri(angle_Up);
        this.i = 0;
        angle = angle_Up;
        setPosition(roadList.get(0).getX(), roadList.get(0).getY());
    }

    @Override
    public void ShowObject(GraphicsContext gc) {
        Move();
        gc.drawImage(this.image, x_pos, y_pos);
    }

    @Override
    public void Render(GraphicsContext gc) {
        Move();
        gc.drawImage(image, this.x_pos, this.y_pos, 50, 50);
        gc.setFill(Color.GRAY);
        gc.fillRect(x_pos + image.getWidth() / 4, y_pos - 3, image.getWidth() / 2, 2);
        gc.setFill(Color.RED);
        gc.fillRect(x_pos + image.getWidth() / 4, y_pos - 3, image.getWidth() / 2 * getBlood() / blood_first, 2);
    }

    public void loadImage(String path) {
        this.image = new Image(path + ".png", 50, 50, false, false);
    }}
