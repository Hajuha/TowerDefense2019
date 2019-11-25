package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.List;

public class SmallerEnemy extends Enemy {
    private final int cash_Small = 50;
    private static final int speed = 2;
    private static final int blood_first = 10;
    private static final int armor_normal = 0;
    private static final Image Small_Img = new Image("file:src/res/Assets/Enemy/SmallEnemy.png",
            40, 40, false, false);

    public SmallerEnemy(List<Point> pointList) {
        super();
        this.cash = cash_Small;
        setFirst_Blood(blood_first);
        setSpeed(speed);
        setArmor(armor_normal);
        this.image = Small_Img;
        loadRoad(pointList);
        setPosition(roadList.get(0).getX(), roadList.get(0).getY());
        setDri(angle_Right);
        this.i = 0;
        angle = getDri();
    }
    @Override
    public void loadImage(String path) {
        this.image = new Image(path + ".png", 50, 50, true, true);
    }
    @Override
    public void ShowObject(GraphicsContext gc) {}

    @Override
    public void Render(GraphicsContext gc) {
        Move();
        gc.drawImage(image, this.x_pos, this.y_pos, 40, 40);
        gc.setFill(Color.GRAY);
        gc.fillRect(x_pos + image.getWidth() / 4, y_pos - 3, image.getWidth() / 2, 2);
        gc.setFill(Color.RED);
        gc.fillRect(x_pos + image.getWidth() / 4, y_pos - 3, image.getWidth() / 2 * getBlood() / blood_first, 2);
    }
}
