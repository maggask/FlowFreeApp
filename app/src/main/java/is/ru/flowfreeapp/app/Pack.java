package is.ru.flowfreeapp.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by margretskristjansdottir on 19.9.14.
 */
public class Pack {
    private String mName;
    private String mDescription;
    private String mFile;

    private List<Puzzle> mPuzzle = new ArrayList<Puzzle>();


    Pack(String name, String description, String file)  {
        mName = name;
        mDescription = description;
        mFile = file;
    }

    String getName() { return mName; }
    String getDescription() { return mDescription; }
    String getFile() { return mFile; }
    List<Puzzle> getPuzzles() { return mPuzzle; }
}
