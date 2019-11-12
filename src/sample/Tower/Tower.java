package sample;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Light;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public abstract class Tower  {
    protected int dame; // sat thuong
    protected int range; // tam ban
    protected double x_pos;
    protected double y_pos;
    protected int cost;
    protected String towerImagePath;
    protected Image image;
    protected List<Bullet> bulletList;
    private boolean isFoundEnemy;
    private sample.Enemy targetEnemy;
    private int i ;

    public Tower(double x_pos, double y_pos) {
        super();
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        i = 0;
        bulletList = new ArrayList<>();
        isFoundEnemy = false;
    }

    public Tower(String towerImagePath) {
        super();
    }

    public Tower() {
        super();
    }

    public void loadImage(String path) {
        this.image = new Image(towerImagePath + ".png", 30, 60,
                true, true);
    }

    public void Shoot(List<sample.Enemy> enemyList)
    {
        if(targetEnemy == null || !utilInRange()){
            isFoundEnemy = false;
        }
        if(isFoundEnemy)
        {
            if(i == 0)  bulletList.add(new Bullet(targetEnemy,(int) x_pos, (int) y_pos));
            i = (i > 20) ? 0 : i + 1;
        }
        else {
            System.out.println("chua tim thay target");
            setTargetEnemy(enemyList); // tim target
        }
    }

    public void setTargetEnemy(List <sample.Enemy> enemyList) {
        if(enemyList.isEmpty()) {
            System.out.println("EnemyList Empty");
            isFoundEnemy = false;
            return;
        }
        isFoundEnemy = false;
        for(int i = 0;i < enemyList.size() ; i++)
        {
            int preRange2 = (int) Math.sqrt(Math.pow(x_pos - enemyList.get(i).getX_pos(), 2)
                    + Math.pow(y_pos - enemyList.get(i).getY_pos(), 2));
            if(preRange2 < range)
            {
                targetEnemy = enemyList.get(i);
                isFoundEnemy = true;
                break;
            }
        }
    }
    public void RenderBullet(GraphicsContext gc,List<sample.Enemy> enemyList)
    {
        if(bulletList == null) return;
        for(Bullet b : bulletList)
        {
            b.Render(gc);
            if(b.isShoot())
            {
                bulletList.remove(b);
                if(bulletList == null) return;
                continue;
            }
            if(b.getTargetEnemy().is_dead())
            {
                isFoundEnemy = false;
                enemyList.remove(b.getTargetEnemy());
                b.setTargetEnemy(enemyList);
                System.out.println("dang tim");
            }

        }
    }
    public void Render(GraphicsContext gc, List<sample.Enemy> enemyList) {
        Shoot(enemyList);
        if(isFoundEnemy) RenderBullet(gc, enemyList);
        gc.drawImage(image, x_pos, y_pos);
//        gc.setStroke(Color.RED);
//        gc.strokeOval(this.x_pos - range + 15 , this.y_pos  - range+ 30, getRange()*2  , getRange()*2);
    }

    public double getY_pos() {
        return y_pos;
    }

    public double getX_pos() {
        return x_pos;
    }

    public void setY_pos(double y_pos) {
        this.y_pos = y_pos;
    }

    public void setX_pos(double x_pos) {
        this.x_pos = x_pos;
    }

    public int getRange() {
        return range;
    }
    public boolean utilInRange() {
        return (range > (int) Math.sqrt(Math.pow(this.x_pos - targetEnemy.getX_pos(), 2)
                + Math.pow(this.y_pos - targetEnemy.getY_pos(), 2))|| targetEnemy.getX_pos() > 1200);
    }
}
