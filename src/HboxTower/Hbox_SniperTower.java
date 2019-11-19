package HboxTower;

import javafx.scene.image.Image;
import sample.SniperTower;

public class Hbox_SniperTower extends HBoxTower {
    private final Image image_Sniper = new Image("file:src/res/Assets/Tower/SniperTower.png",
            40, 70, false, true);
    private final int X_Hbox = 280;
    private final int Y_Hbox = 15;

    public Hbox_SniperTower() {
        super();
        imageView_Hbox.setImage(image_Sniper);
        this.tower = new SniperTower(X_Hbox, Y_Hbox);
        this.Hbox_Tower.setTranslateX(X_Hbox);
        this.Hbox_Tower.setTranslateY(Y_Hbox);
    }


}
