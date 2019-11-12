package HboxTower;

import HboxTower.HBoxTower;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import sample.SniperTower;

public class Hbox_SniperTower extends HBoxTower {
    private final Image image_NormalTower = new Image("file:src/Assets/Tower/SniperTower.png",
            30 , 60, true, true);
    private final int X_Hbox = 0;
    private final int Y_Hbox = 0;
    public Hbox_SniperTower()
    {
        super();
        imageView_Hbox.setImage(image_NormalTower);
        this.tower = new SniperTower(X_Hbox, Y_Hbox);
        this.Hbox_Tower.setTranslateX(X_Hbox);
        this.Hbox_Tower.setTranslateX(Y_Hbox);
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
