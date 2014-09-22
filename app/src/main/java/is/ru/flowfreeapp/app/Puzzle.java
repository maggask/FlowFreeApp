package is.ru.flowfreeapp.app;

/**
 * Created by margretskristjansdottir on 19.9.14.
 */
public class Puzzle {
    private String mSize;
    private String mFlows;

    Puzzle(String size, String flows) {
        mSize = size;
        mFlows = flows;
    }

    String getSize() { return mSize; }
    String getFlows() { return mFlows; }
}
