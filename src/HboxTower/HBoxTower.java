package HboxTower;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import sample.Enemy;
import sample.Tower;

import sample.*;
import sample.Enemy;
import sample.Tower;
import sample.Tower.*;
import sample.NormalEnemy;
import sample.SniperTower;

import java.util.Map;

public abstract class HBoxTower {
    private final static int SCREEN_TITLEMAP = 30;
    protected HBox Hbox_Tower = new HBox();
    protected ImageView imageView_Hbox = new ImageView();
    protected Image image_Hbox;
    protected int x_pos;
    protected int y_pos;
    protected Tower tower;
    private final int BoxTower_WIDTH = 45;
    private final int BoxTower_HEIGHT = 90;
    protected boolean isPut;
    protected boolean isDrag;
    protected boolean canPut;
    protected boolean is_click;
    private int index_tower;

    public HBoxTower() {
        Hbox_Tower.setPrefWidth(BoxTower_WIDTH);
        Hbox_Tower.setPrefHeight(BoxTower_HEIGHT);
        Hbox_Tower.setStyle("-fx-border-color: red;"
                + "-fx-border-width: 0;"
                + "-fx-border-style: solid;");
        insertImage();
        isPut = false;
        isDrag = false;
        canPut = false;
        index_tower = 0;
    }

    void insertImage() {
        this.setupGestureSource();
        Hbox_Tower.getChildren().add(imageView_Hbox);
    }

    public void setX_pos(int x_pos) {
        this.x_pos = x_pos;
    }

    public void setY_pos(int y_pos) {
        this.y_pos = y_pos;
    }

    public void setPosition(int x_pos, int y_pos) {
        this.setX_pos(x_pos);
        this.setY_pos(y_pos);
    }

    public void setupGestureSource() {// Xu ly phan click chuot

        imageView_Hbox.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard db = imageView_Hbox.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                Image sourceImage = imageView_Hbox.getImage();
                content.putImage(sourceImage);
                db.setContent(content);
                event.consume();
            }
        });
        imageView_Hbox.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                imageView_Hbox.setCursor(Cursor.HAND);
            }
        });
    }

    public void setupGestureTarget(Scene scene, int[][] MapTitle, GraphicsContext graphicsContext) { // Xu li phan keo tha
        scene.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasImage()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                    setPosition((int) event.getX(), (int) event.getY());
                    int x_tiles = (int) event.getSceneX() / SCREEN_TITLEMAP;
                    int y_tiles = (event.getSceneY() >= 100) ? ((int) event.getSceneY() - 100) / SCREEN_TITLEMAP : 0;
                    if (MapTitle[y_tiles][x_tiles] == 1 && MapTitle[y_tiles + 1][x_tiles] == 1
                            && MapTitle[y_tiles + 1][x_tiles + 1] == 1 && MapTitle[y_tiles + 1][x_tiles] == 1) {
                        canPut = true;
                    } else canPut = false;
                    isDrag = true;
                }
                event.consume();
            }
        });
        scene.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasImage()) {
                    int x_tiles = (int) event.getSceneX() / SCREEN_TITLEMAP;
                    int y_tiles = (event.getSceneY() >= 100) ? ((int) event.getSceneY() - 100) / SCREEN_TITLEMAP : 0;

                    if (MapTitle[y_tiles][x_tiles] == 1 && MapTitle[y_tiles + 1][x_tiles] == 1
                            && MapTitle[y_tiles + 1][x_tiles + 1] == 1 && MapTitle[y_tiles + 1][x_tiles] == 1) {
                        MapTitle[y_tiles][x_tiles] = 0;
                        MapTitle[y_tiles + 1][x_tiles] = 0;
                        MapTitle[y_tiles + 1][x_tiles + 1] = 0;
                        MapTitle[y_tiles + 1][x_tiles] = 0;
                        tower.setX_pos((x_tiles) * SCREEN_TITLEMAP);
                        tower.setY_pos((y_tiles) * SCREEN_TITLEMAP + 100);
                        isPut = true;
                        canPut = false;
                        isDrag = false;
                    } else {
                        isPut = false;
                        isDrag = false;
                    }
                } else {
                    event.setDropCompleted(false);
                }
                event.consume();
            }
        });
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getX() >= 280 && event.getX() < 370
                        && event.getY() >= 10 && event.getY() < 150) index_tower = 1;
                else if (event.getX() >= 460 && event.getX() < 545
                        && event.getY() >= 10 && event.getY() < 150) index_tower = 3;
                else if (event.getX() >= 380 && event.getX() < 450
                        && event.getY() >= 10 && event.getY() < 150) index_tower = 2;
                if (index_tower != 0) is_click = true;
                System.out.println(index_tower);
            }
        });

    }

    public void Render_Hbox(GraphicsContext gc, int range) {
        if (this.isDrag) {
            if (canPut) {
                gc.setStroke(Color.PAPAYAWHIP);
            } else gc.setStroke(Color.RED);
            gc.strokeOval(x_pos - range, y_pos - range, range * 2, range * 2);
        }
    }

    public Tower getTower() {
        return tower;
    }

    public boolean isPut() {
        return isPut;
    }

    public void setPut(boolean put) {
        isPut = put;
    }

    public HBox getHbox_Tower() {
        return Hbox_Tower;
    }

    public void setIndex_tower(int index_tower) {
        this.index_tower = index_tower;
    }

    public int getIndex_tower() {
        return index_tower;
    }

    public boolean isIs_click() {
        return is_click;
    }

    public void setDrag(boolean drag) {
        this.isDrag = drag;
    }

    public void setIs_click(boolean is_click) {
        this.is_click = is_click;
    }
}
