package sample;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.Tower;

public class SniperTower extends Tower {
    private int newDame;
    private int newRange;
    private int newCost;

    public SniperTower() {
        super();
    }

    public SniperTower(double x_pos, double y_pos) {
        super(x_pos, y_pos);
        this.dame = newDame;
        this.cost = newCost;
        this.towerImagePath = "file:src/Assets/Tower/SniperTower";
    }


}
