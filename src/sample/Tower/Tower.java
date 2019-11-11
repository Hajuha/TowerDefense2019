package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Light;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;


public abstract class Tower {
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
        if(isFoundEnemy)
        {
            if(i == 0)
            {bulletList.add(new Bullet(targetEnemy,(int) x_pos, (int) y_pos));
                System.out.println("da tim thay target : x  " + targetEnemy.x_pos + ",y = " + targetEnemy.y_pos);}
            i = (i > 100) ? 0 : i + 1;
        }
        else {
            setTargetEnemy(enemyList);
            System.out.println("chua tim thay target");
        }
    }

    public void setTargetEnemy(List <sample.Enemy> enemyList) {
        if(enemyList.isEmpty()) {
            System.out.println("EnemyList Empty");
            return;
        }
        targetEnemy = enemyList.get(0);
        int preRange = (int) Math.sqrt(Math.pow(x_pos - enemyList.get(0).getX_pos(), 2)
                + Math.pow(y_pos - enemyList.get(0).getY_pos(), 2));
        for(int i = 1;i < enemyList.size() ; i++)
        {
            int preRange2 = (int) Math.sqrt(Math.pow(x_pos - enemyList.get(i).getX_pos(), 2)
                    + Math.pow(y_pos - enemyList.get(i).getY_pos(), 2));
            if(preRange > preRange2)
            {
//                targetEnemy = enemyList.get(i - 1);
                System.out.println("Da tim thay target");
                break;
            }
            preRange = preRange2;
            targetEnemy = enemyList.get(i - 1);
        }
        isFoundEnemy = true;

    }
    public void RenderBullet(GraphicsContext gc,List<sample.Enemy> enemyList)
    {
//        if(!bulletList.isEmpty())
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
            else if(b.getTargetEnemy().is_dead())
            {
                enemyList.remove(b.getTargetEnemy());
                b.setTargetEnemy(enemyList);
            }

        }
    }
    public void Render(GraphicsContext gc, List<sample.Enemy> enemyList) {

        if(isFoundEnemy) RenderBullet(gc, enemyList);
        Shoot(enemyList);
        loadImage(towerImagePath);
        gc.drawImage(image, x_pos, y_pos);
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
}
