package HboxTower;

import HboxTower.HBoxTower;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import sample.MachineGunTower;

public class Hbox_MachineGunTower extends HBoxTower {
    private final Image image_machine = new Image("file:src/Assets/Tower/a.gif",
            90 , 90, true, true);
    private final int X_Hbox = 455;
    private final int Y_Hbox = 5;
    public Hbox_MachineGunTower()
    {
        super();
        imageView_Hbox.setImage(image_machine);
        this.tower = new MachineGunTower(X_Hbox, Y_Hbox);
        this.Hbox_Tower.setTranslateX(X_Hbox);
        this.Hbox_Tower.setTranslateY(Y_Hbox);
    }


}
