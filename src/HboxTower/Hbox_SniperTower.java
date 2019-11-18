package HboxTower;

import HboxTower.HBoxTower;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import sample.SniperTower;

public class Hbox_SniperTower extends HBoxTower {
    private final Image image_Sniper = new Image("file:src/Assets/Tower/SniperTower.png",
            30 , 60, true, true);
    private final int X_Hbox = 0;
    private final int Y_Hbox = 0;
    public Hbox_SniperTower()
    {
        super();
        imageView_Hbox.setImage(image_Sniper);
        this.tower = new SniperTower(X_Hbox, Y_Hbox);
        this.Hbox_Tower.setTranslateX(X_Hbox);
        this.Hbox_Tower.setTranslateY(Y_Hbox);
    }


}
