package is.ru.flowfreeapp.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by steinar on 14.9.2014.
 */
public class dotPath {
    private Coordinate end = null;
    private Coordinate start = null;
    private List<Coordinate> path = null;

    public dotPath(Coordinate start, Coordinate end) {
        this.end = end;
        this.start = start;
    }

    public void setPath(List<Coordinate> path) {
        this.path = path;
    }

    public List<Coordinate> getPath() {
        return this.path;
    }

    public Coordinate getEnd() {
        return end;
    }

    public Coordinate getStart() {
        return start;
    }

    public void removePath() {
        this.path = null;
    }
}
