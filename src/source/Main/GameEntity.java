package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class GameEntity {
    protected Image image;
    protected int x_pos;
    protected int y_pos;
    public GameEntity() {
        this.x_pos = 0;
        this.y_pos = 0;
    }

    abstract public void loadImage(String path);
    abstract public void ShowObject(GraphicsContext gc);
    abstract public void Render(GraphicsContext gc);

    public int getX_pos() {
        return x_pos;
    }
    public int getY_pos() {
        return y_pos;
    }
    public void setX_pos(int x_pos) {
        this.x_pos = x_pos;
    }
    public void setY_pos(int y_pos) {
        this.y_pos = y_pos;
    }
    public void setPosition(int x_pos, int y_pos) {
        setX_pos(x_pos);
        setY_pos(y_pos);
    }
    public void setImage(Image i) { image = i; }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, x_pos, y_pos);
    }

}
