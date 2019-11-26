package sample;
import sample.GameEntity;

public  abstract  class GameTile extends GameEntity {
    protected int x_pos;
    protected int y_pos;

    public GameTile(int x_pos, int y_pos) {
        super();
        setPosition(x_pos, y_pos);
    }

    public GameTile() {

    }

    public void setY_pos(int y_pos) {
        this.y_pos = y_pos;
    }

    public void setX_pos(int x_pos) {
        this.x_pos = x_pos;
    }

    public int getY_pos() {
        return y_pos;
    }

    public void setPosition(int x_pos, int y_pos) {
        setX_pos(x_pos);
        setY_pos(y_pos);
    }
}
