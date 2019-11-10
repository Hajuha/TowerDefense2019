package sample;


import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import sample.Tower;

import java.util.ArrayList;
import java.util.List;

public class SniperTower extends Tower {
    private int newDame;
    private int newRange;
    private int newCost;
    private final static int SCREEN_TITLEMAP = 30;
    List<Tower> towerList = new ArrayList<>(); //ds tháp được đặt
    private final String SniperTower_IMG = "Tower1";
    ImageView iv;

    public SniperTower() {
        super();
    }

    public SniperTower(double x_pos, double y_pos) {
        super(x_pos, y_pos);
        this.dame = newDame;
        this.cost = newCost;
        this.towerImagePath = "file:src/Assets/Tower/SniperTower";
    }
    /*@Override
    public void loadImage(String path) {
        this.image = new Image(SniperTower_IMG + ".png", 30, 30,
                true, true);

    }


    @Override
    public void Render(GraphicsContext gc) {

        gc.drawImage(image,90, 480);
    }*/

    void setupGestureSource(final ImageView source){

        source.setOnDragDetected(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("DRAG DETECTED");
                /* allow any transfer mode */
                Dragboard db = source.startDragAndDrop(TransferMode.MOVE);

                /* put a image on dragboard */
                ClipboardContent content = new ClipboardContent();

                Image sourceImage = source.getImage();
                content.putImage(sourceImage);
                db.setContent(content);


                iv = source ;


                event.consume();
            }
        });
        source.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                System.out.println("MOUSE ENTERED");
                source.setCursor(Cursor.HAND);
//                    System.out.println("e...: "+e.getSceneX());

            }
        });
    }
    void setupGestureTarget(Scene i, Image[][] imageMap){
        //ImageView target = new ImageView(i);
        i.setOnDragOver(new EventHandler <DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                System.out.println("DRAG OVER");
                Dragboard db = event.getDragboard();

                if(db.hasImage()){
                    event.acceptTransferModes(TransferMode.MOVE);
                }

                event.consume();
            }
        });
        i.setOnDragDropped(new EventHandler <DragEvent>(){
            @Override
            public void handle(DragEvent event) {
                System.out.println("DRAG DROPPED");
                Dragboard db = event.getDragboard();

                if(db.hasImage()){

                    iv.setImage(db.getImage());

                    //Point2D localPoint = target.sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));
                    System.out.println("event : "+event.getSceneX() +", " + event.getSceneY());
                    // System.out.println("localPoint.getX : "+localPoint.getX());
                    //System.out.println("********");
                    int x_tiles = (int) event.getSceneX() / SCREEN_TITLEMAP;
                    int y_tiles = (int) event.getSceneY() / SCREEN_TITLEMAP;

                    //iv.setX((int)(localPoint.getX() - iv.getBoundsInLocal().getWidth()  / 2)  );
                    //iv.setY((int)(localPoint.getY() - iv.getBoundsInLocal().getHeight() / 2) );

                    towerList.add(new SniperTower((x_tiles) *SCREEN_TITLEMAP,(y_tiles-2) * SCREEN_TITLEMAP));
                    event.setDropCompleted(true);
                }else{
                    event.setDropCompleted(false);
                }

                event.consume();
            }
        });
    }

}
