package HboxTower;

import HboxTower.HBoxTower;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import sample.MachineGunTower;

public class Hbox_MachineGunTower extends HBoxTower {
    private final Image image_machine = new Image("file:src/Assets/Tower/a.gif",
            60 , 60, true, true);
    private final int X_Hbox = 200;
    private final int Y_Hbox = 0;
    public Hbox_MachineGunTower()
    {
        super();
        imageView_Hbox.setImage(image_machine);
        this.tower = new MachineGunTower(X_Hbox, Y_Hbox);
        this.Hbox_Tower.setTranslateX(X_Hbox);
        this.Hbox_Tower.setTranslateY(Y_Hbox);
    }

    @Override
    public void Render_Hbox(GraphicsContext gc) {
        if(this.isDrag)
        {
            if(canPut) {
                gc.setStroke(Color.PAPAYAWHIP);
            }
            else gc.setStroke(Color.RED);
            gc.strokeOval(x_pos - tower.getRange() , y_pos - tower.getRange() , tower.getRange()* 2, tower.getRange() * 2);
        }
    }

}
