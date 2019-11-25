package sample;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.Bullet.Sniper_Bullet;

import java.util.ArrayList;
import java.util.List;

public class SniperTower extends Tower {
    private final Image image_NormalTower = new Image("file:src/res/Assets/Tower/SniperTower.png",
            60, 60, true, true);
    private final int newDame = 5;
    private final static int SCREEN_TITLEMAP = 30;
    private final int Range_Sniper = 200;
    List<Tower> towerList = new ArrayList<>(); //ds tháp được đặt
    private final String SniperTower_IMG = "Tower1";
    ImageView iv;

    public SniperTower() {
        super();
    }

    public SniperTower(double x_pos, double y_pos) {
        super(x_pos, y_pos);
        this.dame = newDame;
        this.image = image_NormalTower;
        this.range = Range_Sniper;
    }

    public void Render(GraphicsContext gc, List<sample.Enemy> enemyList) {
        gc.drawImage(image, x_pos, y_pos);
        Shoot(enemyList);
        RenderBullet(gc, enemyList);
    }

    public void Shoot(List<sample.Enemy> enemyList) {
        if (targetEnemy == null || !utilInRange()) {
            isFoundEnemy = false;
        }
        if (isFoundEnemy) {
            if (i == 0) {
                bulletList.add(new Sniper_Bullet(targetEnemy, (int) x_pos, (int) y_pos, indexEnemy, image_Bullt));
                sizeBulet++;
                isFoundEnemy = false;
            }
            i = (i > 50) ? 0 : i + 1;
        } else {
            setTargetEnemy(enemyList); // tim target
        }
    }


}

