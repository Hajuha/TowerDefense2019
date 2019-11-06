package sample;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class NormalEnemy extends Enemy{
    static final int speed = 2;
    static final int blood_first = 16;
    static final int armor_normal = 2;
    static final String Normal_Image = "SmallEnemy";
    static final List<Point> roadList = new ArrayList<>();
    static final int angle_Right = 0;
    static final int angle_Left = 180;
    static final int angle_Up =  90 ;
    static final int angle_Down = 270;
    private Point point;


    static int angle = 0;
    static  int i  = 0 ;
    static int dri ;

    static int add = 1;
    static int up = 1;

    public void loadRoad()
    {
        Point point = new Point(0, 170);
        Point point1 = new Point(200,  170);
        Point point2 = new Point(200,  620);
        Point point3 = new Point(650,  620);
        Point point4 = new Point(650,  80);
        Point point5 = new Point(230,  170);
        roadList.add(point);
        roadList.add(point1);
        roadList.add(point2);
        roadList.add(point3);
        roadList.add(point4);
        roadList.add(point5);
    }
    public NormalEnemy()
    {
        super();
        setFirst_Blood(blood_first);
        setSpeed(speed);
        setArmor(armor_normal);
        loadImage(Normal_Image);
        loadRoad();
//        todo getx + gety
        setPosition(roadList.get(0).getX(), roadList.get(0).getY());
        setDri(angle_Right);
    }
    @Override
    public void Move() {
        /***** To do handle move of Enemy*******/
        switch (getDri())
        {
            case angle_Right:
                x_pos += speed;
                break;
            case angle_Left:
                x_pos -= speed;
                break;
            case angle_Down:
                y_pos += speed;
                break;
            case angle_Up :
                y_pos -= speed;
                break;
        }
        int delta_x = roadList.get(i + 1).getX() - x_pos;
        int delta_y = roadList.get(i + 1).getY() - y_pos;
        if(delta_x == 0 && delta_y == 0 && i < roadList.size())
        {
            System.out.println(i++);
        }
        if(x_pos > 1200) {
            i = 0;
            setPosition(roadList.get(0).getX(), roadList.get(0).getY());
            setDri(angle_Right);
        }
        if(dri != nextRoad())
        {
            dri = nextRoad();
//            angle += 90;
            SnapshotParameters snapshotParameters = new SnapshotParameters();
            snapshotParameters.setFill(Color.TRANSPARENT);
            ImageView imageView = new ImageView(image);
            imageView.setRotate(imageView.getRotate() + 90);
            image = imageView.snapshot(snapshotParameters, null);
        }
    }
    public int nextRoad()
    {
        int delta_x = roadList.get(i + 1).getX() - roadList.get(i).getX();
        int delta_y = roadList.get(i + 1).getY() - roadList.get(i).getY();
        if(delta_x == 0 && delta_y > 0)
        {
            return angle_Down;
        }
        if(delta_x == 0 && delta_y < 0)
        {
            return angle_Up;
        }
        if(delta_x > 0 && delta_y == 0)
        {
            return angle_Right;
        }
        if(delta_x < 0 && delta_y == 0)
        {
            return angle_Left;
        }
        return  0;

    }
    @Override
    public void ShowObject(GraphicsContext gc) {
        Move();
        gc.drawImage(this.image,x_pos, y_pos);
    }

    @Override
    public void Render(GraphicsContext gc) {

//        gc.clearRect(x_pos  , y_pos , 50, 50);
        Move();
        gc.drawImage(image, x_pos,y_pos, 50, 50);
        gc.setFill(Color.RED);
        gc.fillRect(x_pos + image.getWidth()/4 , y_pos - 3, image.getWidth()/2 * getBlood()/blood_first, 2);
    }

    public static void setDri(int dri) {
        NormalEnemy.dri = dri;
    }

    public static int getDri() {
        return dri;
    }
    public Point getPosition()
    {
        point = new Point(x_pos, y_pos);
        return point;
    }
}
