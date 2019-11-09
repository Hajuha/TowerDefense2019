package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SniperTower extends Tower {
    private final String SniperTower_IMG = "Tower1";
    public SniperTower()
    {
        super(100, 470);
        loadImage(SniperTower_IMG);
    }

    public SniperTower(int x_pos, int y_pos) {
        super(x_pos, y_pos);
    }

    @Override
    public void shoot() {

    }

    @Override
    public void loadImage(String path) {
        this.image = new Image(SniperTower_IMG + ".png", 60, 60,
                true, true);

    }

    @Override
    public void ShowObject(GraphicsContext gc) {

    }

    @Override
    public void Render(GraphicsContext gc) {

        gc.drawImage(image,65, 480);
    }
}
