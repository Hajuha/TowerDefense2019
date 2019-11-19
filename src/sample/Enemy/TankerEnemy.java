package sample;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import sample.Enemy;
public class TankerEnemy extends Enemy {
    private final int cash_tank = 230;

    private static final int speed = 1;
    private static final int blood_first =400;
    private static final int armor_tank = 4;
    private static final String Tank_Image = "file:src/Assets/Enemy/tank";
    private static final Image  Tank_Img = new Image("file:src/Assets/Enemy/tank.png",
            60, 60, false, false);
    public TankerEnemy(List<Point> pointList)
    {
        super();
        this.cash =cash_tank;
        setFirst_Blood(blood_first);
        setSpeed(speed);
        setArmor(armor_tank);
        this.image = Tank_Img;
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
