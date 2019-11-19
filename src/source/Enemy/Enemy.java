package sample;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import sample.*;
import sample.Enemy;
import sample.Tower;
import  sample.NormalEnemy;
import sample.SniperTower;

public abstract class Enemy extends GameEntity {
    protected int speed ;
    protected int Blood;
    protected int armor;
    private List<Enemy> normalEnemies = new ArrayList<>();
    static final int angle_Right = 0;
    static final int angle_Left = 180;
    static final int angle_Up =  90 ;
    static final int angle_Down = 270;
    protected Point point;
    protected List<Point> roadList = new ArrayList<>();
    protected int angle;
    protected  int i ;
    protected int dri ;
    protected int cashIncrease = 0;
    protected int bloodDecrease = 0;
    protected int cash;
    public void adds(Enemy normalEnemy)
    {
        normalEnemies.add(normalEnemy);
    }
    public void loadRoad(List<Point> pointList)
    {
        roadList.addAll(pointList);
    }
    public void Move() {
        /***** To do handle move of Enemy*******/
        int next_road = nextRoad();
        if(this.dri != next_road)
        {
            this.dri = next_road;
            SnapshotParameters snapshotParameters = new SnapshotParameters();
            snapshotParameters.setFill(Color.TRANSPARENT);
            ImageView imageView = new ImageView(this.image);
            imageView.setRotate(angle - this.dri);
            angle = this.dri;
            this.image = imageView.snapshot(snapshotParameters, null);
        }
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
        if(delta_x == 0 && delta_y == 0 && i < roadList.size())  i ++;
    }
    public int nextRoad()
    {
        int delta_x =this.roadList.get(i + 1).getX() - this.roadList.get(i).getX();
        int delta_y = this.roadList.get(i + 1).getY() - this.roadList.get(i).getY();
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
    public Point getPosition()
    {
        point = new Point(x_pos + 15, y_pos + 15);
        return point;
    }
    public void RenderList(GraphicsContext gc)
    {
        for (int i = 0; i < normalEnemies.size(); i ++)
        {
            if(normalEnemies.get(i).is_dead()) {
                cashIncrease += normalEnemies.get(i).cash;
                normalEnemies.remove(i);
            }
            else if (normalEnemies.get(i).is_over()){
                normalEnemies.remove(i);
                bloodDecrease += 1;
            }
            else {
                normalEnemies.get(i).Render(gc);
            }
        }
    }
    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getArmor() {
        return armor;
    }

    public int getBlood() {
        return Blood;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setFirst_Blood(int first_Blood) {
        Blood = first_Blood;
    }
    public void bleed(int blood_delta)
    {
        this.Blood -= ((double) blood_delta * (double) ( 10 - this.armor) /10);
    }
    public boolean is_dead()
    {
        return  (this.Blood <= 0  );
    }
    //kiểm tra địch có vượt qua các tháp ko
    public boolean is_over(){
        return (this.x_pos >= 1220 || this.y_pos >= 850 || this.x_pos <= -30 || this.y_pos <= - 30);
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

    public void getRoadList() {
    }
    public List<Enemy> getListEnemy() {
        return normalEnemies;
    }

    @Override
    public int getX_pos() {
        return super.getX_pos();
    }

    @Override
    public int getY_pos() {
        return super.getY_pos();
    }
}
