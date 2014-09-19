package is.ru.flowfreeapp.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    private Global mGlobals = Global.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            List<Pack> packs = new ArrayList<Pack>();
            readPack(getAssets().open("packs/packs.xml"), packs);
            mGlobals.mPacks = packs;

            List<Puzzle> puzzles = new ArrayList<Puzzle>();
            readEasy(getAssets().open("packs/easy.xml"), puzzles);
            mGlobals.mPuzzles = puzzles;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readPack(InputStream is, List<Pack> packs) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);
            NodeList nList = doc.getElementsByTagName("pack");

            for (int c = 0; c < nList.getLength(); ++c) {
                Node nNode = nList.item(c);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eNode = (Element) nNode;
                    String name = eNode.getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
                    String description = eNode.getElementsByTagName("description").item(0).getFirstChild().getNodeValue();
                    String file = eNode.getElementsByTagName("file").item(0).getFirstChild().getNodeValue();
                    packs.add(new Pack(name, description, file));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readEasy(InputStream is, List<Puzzle> puzzles) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);
            NodeList nList = doc.getElementsByTagName("puzzle");

            for (int c = 0; c < nList.getLength(); ++c) {
                Node nNode = nList.item(c);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eNode = (Element) nNode;
                    String size = eNode.getElementsByTagName("size").item(0).getFirstChild().getNodeValue();
                    String flow = eNode.getElementsByTagName("flow").item(0).getFirstChild().getNodeValue();
                    puzzles.add(new Puzzle(size, flow));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buttonClick( View view ) {
        Sound s = new Sound();
        s.playSound(this);
        TextView button = (TextView) view;
        int id = button.getId();
        if (id == R.id.buttonPlay) {

        startActivity(new Intent(this, PlayActivity.class));
        }
        else if (id == R.id.buttonSettings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }
        else if (id == R.id.buttonInstructions) {
            startActivity(new Intent(this, InstructionsActivity.class));
        }
    }
}
