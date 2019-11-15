package sample;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import sample.Enemy;
public class TankerEnemy extends Enemy {
    private static final int speed = 1;
    private static final int blood_first =500;
    private static final int armor_tank = 2;
    private static final String Tank_Image = "file:src/Assets/Enemy/tank";
    public TankerEnemy(List<Point> pointList)
    {
        super();
        setFirst_Blood(blood_first);
        setSpeed(speed);
        setArmor(armor_tank);
        loadImage(Tank_Image);
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
        gc.drawImage(image,this.x_pos, this.y_pos, 50, 50);
        gc.setFill(Color.GRAY);
        gc.fillRect(x_pos + image.getWidth()/4 , y_pos - 3, image.getWidth()/2, 2);
        gc.setFill(Color.RED);
        gc.fillRect(x_pos + image.getWidth()/4 , y_pos - 3, image.getWidth()/2 * getBlood()/blood_first, 2);
    }
    public void loadImage(String path) {
        this.image = new Image(path + ".png", 50, 50, true, true);
    }
}
