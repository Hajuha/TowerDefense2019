package sample;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.transform.Rotate;
import sample.Tower;
import sample.Enemy;

import javax.crypto.Cipher;
import java.util.ArrayList;
import java.util.List;

public class NormalTower extends Tower {
    private final Image image_NormalTower = new Image("file:src/Assets/Tower/normal.gif",
            60 , 80, true, true);
    private int newDame;
    private int newRange;
    private int newCost;
    private final static int SCREEN_TITLEMAP = 30;
    private final int Range_Normal = 250;
    List<sample.Tower> towerList = new ArrayList<>(); //ds tháp được đặt
    private final String SniperTower_IMG = "Tower1";
    ImageView iv;

    public NormalTower() {
        super();
    }

    public NormalTower(double x_pos, double y_pos) {
        super(x_pos, y_pos);
        this.dame = newDame;
        this.cost = newCost;
        this.image = image_NormalTower;
        this.range = Range_Normal;
    }

    public void Render(GraphicsContext gc, List<sample.Enemy> enemyList) {
        gc.drawImage(image_NormalTower, x_pos, y_pos);
        Shoot(enemyList);

        RenderBullet(gc, enemyList);
    }
    public void Shoot(List<sample.Enemy> enemyList)
    {
        if(targetEnemy == null || !utilInRange()){
            isFoundEnemy = false;
        }
        if(isFoundEnemy)
        {
            if(i == 0)
            {
                bulletList.add(new NormalBullet(targetEnemy, (int)x_pos ,(int) y_pos,indexEnemy, image_Bullt ));
                sizeBulet ++;
                isFoundEnemy = false;
            }
            i = (i > 15) ? 0 : i + 1;
        }
        else {
            setTargetEnemy(enemyList); // tim target
        }
    }
}
