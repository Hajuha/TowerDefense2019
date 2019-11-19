package sample.Bullet;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import sample.Bullet.Bullet;

public class NormalBullet extends Bullet {
    final static int dame = 10;
    final static int speed_bullet = 5;
    public NormalBullet(sample.Enemy enemy, int x_pos, int y_pos, int indexListEnemy, Image image_Bullt) {
        super(enemy, x_pos, y_pos, indexListEnemy, image_Bullt);
        this.speed = speed_bullet;
    }

    @Override
    public void Render(GraphicsContext gc) {
        if(!isshoot)
        {
            move();
            gc.setFill(Color.RED);
            gc.fillOval(x_pos, y_pos, 5, 5);
        }
        isshoot = isShoot();
        if(isshoot) {
            TargetEnemy.bleed(dame);
        }
    }
}
