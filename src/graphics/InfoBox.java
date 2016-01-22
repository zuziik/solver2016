package graphics;

import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zuzka on 20.1.2016.
 */
public class InfoBox extends Label {
    private final List<String> info;
    private String givens;

    public InfoBox() {
        this.info = new ArrayList<>();
        this.givens = "#Givens: 0\n";
    }

    public void changeGivens(int givens) {
        this.givens = "#Givens: "+givens+"\n";
        this.update();
    }

    public void update() {
        String text = "";
        for (String t : info) {
            text = t + text;
        }
        text = givens + text;
        super.setText(text);
    }

    public void addInfo(String text) {
        this.info.add(text);
        if (this.info.size() > 5) {
            this.info.remove(0);
        }
        this.update();
    }
}
