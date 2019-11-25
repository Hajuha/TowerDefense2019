package source.Main;

import sample.Point;

import java.util.ArrayList;
import java.util.List;

public class Road {
    private List<sample.Point> roadList ;
    public Road()
    {
        roadList = new ArrayList<>();
    }
    public void add(sample.Point point){
        roadList.add(point);
    }
    public List<Point> getRoadList() {
        return roadList;
    }
}
