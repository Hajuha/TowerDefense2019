package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
enum Direction{
    LEFT(180), RIGHT(0), UP(270), DOWN(90);
    int direction;
     Direction(int i){
        this.direction = i;
    }

    public int getDirection() {
        return direction;
    }
}

public abstract class Enemy extends GameEntity {
    protected int Speed ;
    protected int Blood;
    protected int armor;

    public abstract void Move();

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
        return Speed;
    }

    public void setSpeed(int speed) {
        Speed = speed;
    }

    public void setFirst_Blood(int first_Blood) {
        Blood = first_Blood;
    }
    public void bleed(int blood_delta)
    {
        this.Blood -= blood_delta;
    }
    public boolean is_dead()
    {
        return  (this.Blood <= 0);
    }

    @Override
    public void loadImage(String path) {
        this.image = new Image(path + ".png", 50, 50, true, true);
    }

    public abstract void RenderList(GraphicsContext mainGraphic);

    public abstract Point getPosition();
}
