package sample.Bullet;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import sample.Enemy;
import sample.GameTile;
import sample.Point;

import java.util.List;

public abstract class Bullet extends GameTile {
    protected SnapshotParameters snapshotParameters = new SnapshotParameters();

    protected Enemy TargetEnemy;
    protected boolean is_found;
    protected int speed;
    protected double angle;
    protected double sinX;
    protected double cosX;
    protected int indexListEnemy;
    protected boolean isshoot;
    protected ImageView imageView = new ImageView();

    public Point Destination = new Point(0, 0);

    public Bullet(Enemy enemy, int x_pos, int y_pos, int indexListEnemy, Image image_Bullt) {
        super(x_pos, y_pos);
        this.image = image_Bullt;
        imageView.setImage(image);
        is_found = false;
        angle = 0;
        sinX = 0;
        cosX = 1;
        this.indexListEnemy = indexListEnemy;
        isshoot = false;
        snapshotParameters.setFill(Color.TRANSPARENT);

        setTargetEnemy(enemy);
        setDestination(enemy.getPosition());
    }

    public void setBullet(sample.Enemy enemy, int x_pos, int y_pos) {
    }

    public Bullet getBullet() {
        return this;
    }

    public Bullet(int x_pos, int y_pos) {
        super(x_pos, y_pos);
    }

    @Override
    public void loadImage(String path) {
//        this.image = new Image(Bullet_Img + ".png", 30, 30, true, true);
    }

    public void setSinX(double sinX) {
        this.sinX = sinX;
    }

    public void setCosX(double cosX) {
        this.cosX = cosX;
    }

    public void setAngle() {
        setDestination(TargetEnemy.getPosition());
        double del = Math.sqrt(Math.pow((x_pos - Destination.getX()), 2) +
                Math.pow(y_pos - Destination.getY(), 2));
        sinX = (Destination.y - y_pos == 0) ? 0 : (Destination.y - (double) y_pos) / del;
        cosX = (Destination.x - x_pos == 0) ? 0 : (Destination.x - (double) x_pos) / del;
        double angle2 = Math.abs(Math.asin(sinX) * 180 / Math.PI);
        if (sinX >= 0 && cosX >= 0) {
            angle2 = 270 - angle2;
        } else if (sinX >= 0 && cosX <= 0) {
            angle2 = 90 + angle2;
        } else if (sinX <= 0 && cosX >= 0) {
            angle2 = 270 + angle2;
        } else if (sinX <= 0 && cosX <= 0) {
            angle2 = 90 - angle2;
        }
        double delta_angle = (angle - angle2 > 180) ? (angle - angle2 - 360) : angle - angle2;
        angle = angle2;
        this.imageView.setRotate(imageView.getRotate() + delta_angle);
        image = imageView.snapshot(snapshotParameters, null);
    }

    @Override
    public void ShowObject(GraphicsContext gc) { }

    public void move() {
        x_pos += ((double) speed) * cosX;
        y_pos += ((double) speed) * sinX;
        setAngle();
    }
    @Override
    public abstract void Render(GraphicsContext gc);
    public void setDestination(Point destination) {
        this.Destination = destination;
    }
    public Point getDestination() {
        return Destination;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public boolean isShoot() {
        return (x_pos + 15 >= Destination.getX() && x_pos - 15 <= Destination.getX()
                && y_pos + 15 >= Destination.getY() && y_pos - 15 <= Destination.getY()
        );
    }

    public void setTargetEnemy(List<Enemy> listTarget) {
        if (TargetEnemy.is_dead() && !listTarget.isEmpty() && indexListEnemy < listTarget.size()) // trong truong hop muc tieu da chet
        {
            int preRange = (int) Math.sqrt(Math.pow(x_pos - listTarget.get(indexListEnemy).getX_pos(), 2)
                    + Math.pow(y_pos - listTarget.get(indexListEnemy).getY_pos(), 2));
            for (int i = indexListEnemy; i < listTarget.size(); i++) {
                int preRange2 = (int) Math.sqrt(Math.pow(x_pos - listTarget.get(i).getX_pos(), 2)
                        + Math.pow(y_pos - listTarget.get(i).getY_pos(), 2));
                if (preRange > preRange2 && preRange2 < 250) {
                    preRange = preRange2;
                    indexListEnemy = i;
                }
            }
            if (preRange < 250) TargetEnemy = listTarget.get(indexListEnemy);
            System.out.println(preRange);
        }
    }

    public void setTargetEnemy(Enemy targetEnemy) {
        TargetEnemy = targetEnemy;
    }

    public Enemy getTargetEnemy() {
        return TargetEnemy;
    }


}
