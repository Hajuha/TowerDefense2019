package HboxTower;

import HboxTower.HBoxTower;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import sample.GameField;
import sample.NormalTower;
import sample.SniperTower;

public class Hbox_NormalTower extends HBoxTower {
    private final Image image_NormalTower = new Image("file:src/Assets/Tower/normal.gif",
                                    60 , 60, true, true);
    private final int X_Hbox = 400;
    private final int Y_Hbox = 0;
    public Hbox_NormalTower()
    {
        super();
        imageView_Hbox.setImage(image_NormalTower);
        this.tower = new NormalTower(X_Hbox, Y_Hbox);
        this.Hbox_Tower.setTranslateX(X_Hbox);
        this.Hbox_Tower.setTranslateY(Y_Hbox);
    }

}
