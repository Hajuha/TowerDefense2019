package sample;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Bullet extends GameTile{
    final static String Bullet_Img = "RocketBullet";
    final static int dame = 10;
    final static int speed_bullet = 3;
    final static int range = 270;
    protected int speed;
    private double angle;
    private double sinX;
    private double cosX;

    public Point Destination  = new Point(0, 0);

    public Bullet( Image bullet)
    {
        super(100, 450);
        setImage(bullet);
        setSpeed(speed_bullet);
        angle = 0;
        sinX = 0;
        cosX = 1;
    }
    public Bullet(int x_pos, int y_pos) {
        super(x_pos, y_pos);
    }

    @Override
    public void loadImage(String path) {
        this.image = new Image(Bullet_Img + ".png", 30, 30, true, true);
    }

    public void setSinX(double sinX) {
        this.sinX = sinX;
    }

    public void setCosX(double cosX) {
        this.cosX = cosX;
    }
    public void setAngle()
    {
        double delta_angle = 0;
        double del = Math.sqrt(Math.pow((x_pos - Destination.getX()), 2) +
                                Math.pow(y_pos - Destination.getY(), 2))   ;
        sinX = (Destination.y - y_pos == 0) ? 0 : (Destination.y - (double) y_pos)/del;
        cosX = (Destination.x - x_pos == 0) ? 0 : (Destination.x - (double) x_pos)/del;
        if(sinX > 0 && cosX > 0) delta_angle = 0;
        if(sinX < 0 && cosX < 0) delta_angle =0 ;
        if(sinX > 0 && cosX < 0) delta_angle =0 ;
        if(sinX < 0 && cosX < 0) delta_angle =0 ;
//        angle += delta;

    }

    @Override
    public void ShowObject(GraphicsContext gc) {

    }
    public void move()
    {
        setAngle();
        x_pos += speed * cosX;
        y_pos += speed * sinX;
    }


    @Override
    public void Render(GraphicsContext gc) {
//        if(x_pos != Destination.getX() && y_pos != Destination.getY()) {

//        System.out.println(x_pos);
//            SnapshotParameters snapshotParameters = new SnapshotParameters();
//            snapshotParameters.setFill(Color.TRANSPARENT);
//            ImageView imageView = new ImageView(image);
//            imageView.setRotate(imageView.getRotate() + angle);
//            image = imageView.snapshot(snapshotParameters, null);
        if(!isShoot())
        {
            move();
            gc.drawImage(image, x_pos + 5, y_pos);

        }
        else {
        }

//        }
    }

    public void setDestination(Point destination) {
        this.Destination = destination;
    }

    public Point getDestination() {
        return Destination;
    }


    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public boolean isShoot()
    {
        return (x_pos + 35 >= Destination.getX() && x_pos - 35 <= Destination.getX()
                && y_pos + 35 >= Destination.getY() && y_pos - 35 <= Destination.getY()
        );
    }

}
