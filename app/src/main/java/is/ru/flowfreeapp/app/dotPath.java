package is.ru.flowfreeapp.app;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by steinar on 14.9.2014.
 */
public class dotPath {
    private Coordinate end = null;
    private Coordinate start = null;
    private List<Coordinate> path = new ArrayList<Coordinate>();
    private int pointColor;
    private boolean isConnected = false;

    public dotPath(Coordinate start, Coordinate end, int color) {
        this.end = end;
        this.start = start;
        this.pointColor = color;
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

    public int getPathColor() { return pointColor; }

    public Coordinate getStart() {
        return start;
    }

    public void setConnected(boolean connected) { this.isConnected = connected; }

    public boolean getConnected() { return this.isConnected; }

    public void reset() {
        if (this.path != null)
            this.path.clear();
    }

    public boolean isEmpty() {
        return path.isEmpty();
    }

    public void append(Coordinate co) {
        int idx = path.indexOf(co);
        if (idx >= 0) {
            for (int i = path.size() - 1; i > idx; --i) {
                path.remove(i);
            }
        }
        else {
            path.add(co);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof dotPath)) {
            return false;
        }
        dotPath dP = (dotPath)other;
        return this.getStart().equals(dP.getStart());
    }
}
