package sample;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.effect.Light;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;


public abstract class Tower extends GameTile{
    protected int dame; // sat thuong
    protected int range; // tam ban

    public Tower(int x_pos, int y_pos) {
        super(x_pos, y_pos);
    }

    abstract public void shoot();

//    abstract public
    @Override
    public void loadImage(String path) {
    this.image = new Image(path + ".png", 50, 50, true, true);
}


}
