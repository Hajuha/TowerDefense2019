package sample;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.List;
import sample.Tower;
import sample.Enemy;

public class MachineGunTower extends Tower {
    private final Image image_MachineGun = new Image("file:src/res/Assets/Tower/a.gif",
            80, 80, false, true);
    private final Image image_Gun = new Image("file:src/res/Assets/Tower/b.gif", 80, 80, false, true);
    private int newDame;
    private int newRange;
    private int newCost;
    private Image gun;
    private double angle;
    private final static int SCREEN_TITLEMAP = 30;
    private final int Range_Sniper = 250;
    List<Tower> towerList = new ArrayList<>(); //ds tháp được đặt
    private ImageView iv = new ImageView();
    private SnapshotParameters snapshotParameters = new SnapshotParameters();
    private Rotate rotate = new Rotate();
    private int i;

    public MachineGunTower() {
        super();
    }

    @Override
    public void Shoot(List<Enemy> enemyList) { }

    private double x_posGun;
    private double y_posGun;

    public MachineGunTower(double x_pos, double y_pos) {
        super(x_pos, y_pos);
        this.image = image_MachineGun;
        this.gun = image_Gun;
        this.range = Range_Sniper;
        x_posGun = x_pos;
        y_posGun = y_pos;
        this.dame = 1;
        snapshotParameters.setFill(Color.TRANSPARENT);
        iv.setImage(gun);
        i = 0;
        angle = 0;
    }

    public void RotateGun() {
        if (targetEnemy == null) {
            iv.setRotate(0);
            return;
        }
        double delta_x = x_pos - ((Enemy) targetEnemy).getX_pos();
        double delta_y = y_pos - ((Enemy) targetEnemy).getY_pos();
        angle = Math.atan(delta_x / delta_y);
        double preW = gun.getHeight();
        double preH = gun.getHeight();
        iv.setRotate(angle);
        gun = iv.snapshot(snapshotParameters, null);
    }

    public void Render(GraphicsContext gc, List<sample.Enemy> enemyList) {
        if (targetEnemy != null) {
            double delta_x = x_posGun - ((Enemy) targetEnemy).getX_pos();
            double delta_y = y_posGun - ((Enemy) targetEnemy).getY_pos();
            double D = Math.pow(delta_x, 2) + Math.pow(delta_y, 2);
            double sinX = delta_x / D;
            angle = Math.atan(delta_y / delta_x) * 180 / Math.PI;
            double angle2;
            angle2 = angle;
            if (sinX > 0) angle2 -= 90;
            if (sinX < 0) angle2 += 90;
            double preW = gun.getHeight();
            double preH = gun.getHeight();
            iv.setRotate(angle2);
            gun = iv.snapshot(snapshotParameters, null);
            x_pos -= (gun.getWidth() - preW) / 2;
            y_pos -= (gun.getHeight() - preH) / 2;
        }
        gc.drawImage(image, x_posGun, y_posGun);
        Shoot(enemyList, gc);
        gc.drawImage(gun, x_pos, y_pos);
    }

    public void Shoot(List<sample.Enemy> enemyList, GraphicsContext graphicsContext) {
        if (targetEnemy == null || !utilInRange()) {
            isFoundEnemy = false;
        } else if (((Enemy) targetEnemy).is_dead()) {
            enemyList.remove(targetEnemy);
            isFoundEnemy = false;
        }
        if (isFoundEnemy) {
            if (i == 0) {
                graphicsContext.setStroke(Color.RED);
                graphicsContext.strokeLine(x_posGun + 40, y_posGun + 45, ((Enemy) targetEnemy).x_pos + 25, ((Enemy) targetEnemy).y_pos + 20);
                graphicsContext.setFill(Color.YELLOW);
                graphicsContext.fillOval(((Enemy) targetEnemy).x_pos + 18, ((Enemy) targetEnemy).y_pos + 18, 10, 10);
                ((Enemy) targetEnemy).bleed(2);
            }
        } else {
            setTargetEnemy(enemyList); // tim target
        }
    }

}
