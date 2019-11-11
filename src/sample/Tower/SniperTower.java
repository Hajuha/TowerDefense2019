package sample;


import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import sample.Tower;

import java.util.ArrayList;
import java.util.List;

public class SniperTower extends Tower {
    private int newDame;
    private int newRange;
    private int newCost;
    private final static int SCREEN_TITLEMAP = 30;
    List<Tower> towerList = new ArrayList<>(); //ds tháp được đặt
    private final String SniperTower_IMG = "Tower1";
    ImageView iv;

    public SniperTower() {
        super();
    }

    public SniperTower(double x_pos, double y_pos) {
        super(x_pos, y_pos);
        this.dame = newDame;
        this.cost = newCost;
        this.towerImagePath = "file:src/Assets/Tower/SniperTower";
    }
    /*@Override
    public void loadImage(String path) {
        this.image = new Image(SniperTower_IMG + ".png", 30, 30,
                true, true);
    }
    @Override
    public void Render(GraphicsContext gc) {
        gc.drawImage(image,90, 480);
    }*/


}