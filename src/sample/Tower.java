package sample;

import javafx.scene.effect.Light;


public abstract class Tower extends GameTile{
    protected int dame; // sat thuong
    protected int range; // tam ban

    public Tower(int x_pos, int y_pos) {
        super(x_pos, y_pos);
    }

    abstract public void shoot();

//    abstract public


}
