package sample.Bullet;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.Enemy;
import sample.Bullet.Bullet;

public class Sniper_Bullet extends Bullet {
    final static int dame = 5;
    final static int speed_bullet = 4;

    public Sniper_Bullet(Enemy enemy, int x_pos, int y_pos, int indexListEnemy, Image image_Bullt) {
        super(enemy, x_pos, y_pos, indexListEnemy, image_Bullt);
        this.speed = speed_bullet;
    }

    @Override
    public void Render(GraphicsContext gc) {
        if (!isshoot) {
            move();
            gc.drawImage(image, x_pos, y_pos);
        }
        isshoot = isShoot();
        if (isshoot) {
            TargetEnemy.bleed(dame);
        }
    }
}
