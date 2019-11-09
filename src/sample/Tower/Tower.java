package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Light;

import javafx.scene.image.Image;


public abstract class Tower {
    protected int dame; // sat thuong
    protected int range; // tam ban
    protected double x_pos;
    protected double y_pos;
    protected int cost;
    protected String towerImagePath;
    protected Image image;

    public Tower(double x_pos, double y_pos) {
        super();
        this.x_pos = x_pos;
        this.y_pos = y_pos;
    }

    public Tower(String towerImagePath) {
        super();
    }

    public Tower() {
        super();
    }

    public void loadImage(String path) {
        this.image = new Image(towerImagePath + ".png", 120, 120,
                true, true);
    }

    public void Render(GraphicsContext gc) {
        loadImage(towerImagePath);
        gc.drawImage(image, x_pos, y_pos);
    }

}
