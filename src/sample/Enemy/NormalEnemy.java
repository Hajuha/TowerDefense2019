package sample;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

public class NormalEnemy extends Enemy{
    private List<NormalEnemy> normalEnemies = new ArrayList<>();
    static final int speed = 1;
    static final int blood_first = 16;
    static final int armor_normal = 2;
    static final String Normal_Image = "file:src/Assets/Enemy/SmallEnemy";

    static final int angle_Right = 0;
    static final int angle_Left = 180;
    static final int angle_Up =  90 ;
    static final int angle_Down = 270;
    private Point point;
    private List<Point> roadList = new ArrayList<>();
    private int angle = 0;
    private  int i ;
    private int dri ;

    private int add;
    private int up ;
    public void adds(NormalEnemy normalEnemy)
    {
        normalEnemies.add(normalEnemy);
    }

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
        setPosition(roadList.get(0).getX(), roadList.get(0).getY());
        setDri(angle_Right);
        i = 0;
        up = 1;
        add = 1;
        angle = 0;
    }
    @Override
    public void Move() {
        /***** To do handle move of Enemy*******/
        switch (getDri())
        {
            case angle_Right:
                this.x_pos += speed;
                break;
            case angle_Left:
                this.x_pos -= speed;
                break;
            case angle_Down:
                this.y_pos += speed;
                break;
            case angle_Up :
                this.y_pos -= speed;
                break;
        }
        int delta_x = this.roadList.get(i + 1).getX() - this.x_pos;
        int delta_y = this.roadList.get(i + 1).getY() - this.y_pos;
        if(delta_x == 0 && delta_y == 0 && i < roadList.size())
        {
            System.out.println(i++);
        }
        if(this.x_pos > 1200) {
            i = 0;
            setPosition(this.roadList.get(0).getX(), this.roadList.get(0).getY());
            setDri(angle_Right);
        }
        if(this.dri != nextRoad())
        {
            this.dri = nextRoad();
//            angle += 90;
            SnapshotParameters snapshotParameters = new SnapshotParameters();
            snapshotParameters.setFill(Color.TRANSPARENT);
            ImageView imageView = new ImageView(this.image);
            imageView.setRotate(imageView.getRotate() + 90);
            this.image = imageView.snapshot(snapshotParameters, null);
        }
    }
    public int nextRoad()
    {
        int delta_x =this.roadList.get(i + 1).getX() - this.roadList.get(i).getX();
        int delta_y = this.roadList.get(i + 1).getY() - this.roadList.get(i).getY();
        if(delta_x == 0 && delta_y > 0)
        {
            //System.out.println(x_pos + " " + y_pos);
            //System.out.println("DOWN");
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
        Move();
        gc.drawImage(image,this.x_pos, this.y_pos, 50, 50);
        gc.setFill(Color.RED);
        gc.fillRect(x_pos + image.getWidth()/4 , y_pos - 3, image.getWidth()/2 * getBlood()/blood_first, 2);
        gc.setStroke(Color.GREEN);
        gc.strokeLine(x_pos + 25, y_pos + 25, 600, 360);
    }


    public Point getPosition()
    {
        point = new Point(x_pos + 15, y_pos + 20);
        return point;
    }
    public void RenderList(GraphicsContext gc)
    {
        for (NormalEnemy normalEnemy : normalEnemies)
        {
            normalEnemy.Render(gc);
        }
    }

    public int getDri() {
        return dri;
    }
    public void setDri(int dri) {
        this.dri = dri;
    }
    public Enemy get(int index)
    {
        return normalEnemies.get(index);
    }
    public int size()
    {
        return normalEnemies.size();
    }
}
