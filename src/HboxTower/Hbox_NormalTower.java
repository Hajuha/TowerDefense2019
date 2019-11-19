package HboxTower;

import javafx.scene.image.Image;
import sample.NormalTower;

public class Hbox_NormalTower extends HBoxTower {
    private final Image image_NormalTower = new Image("file:src/res/Assets/Tower/normal.gif",
            40, 70, false, true);
    private final int X_Hbox = 380;
    private final int Y_Hbox = 15;

    public Hbox_NormalTower() {
        super();
        imageView_Hbox.setImage(image_NormalTower);
        this.tower = new NormalTower(X_Hbox, Y_Hbox);
        this.Hbox_Tower.setTranslateX(X_Hbox);
        this.Hbox_Tower.setTranslateY(Y_Hbox);
    }
}
