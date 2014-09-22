package is.ru.flowfreeapp.app;

/**
 * Created by yngvi on 5.9.2014.
 */
public class Coordinate {

    private int m_col;
    private int m_row;

    Coordinate(int col, int row) {
        m_col = col;
        m_row = row;
    }


    public int getCol() {
        return m_col;
    }

    public int getRow() {
        return m_row;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Coordinate)) {
            return false;
        }
        Coordinate otherCo = (Coordinate)other;
        return otherCo.getCol() == this.getCol() && otherCo.getRow() == this.getRow();
    }

    @Override
    public Coordinate clone() {
        Integer c = new Integer(this.m_col);
        Integer r = new Integer(this.m_row);
        Coordinate newCo = new Coordinate(c, r);
        return newCo;
    }
}