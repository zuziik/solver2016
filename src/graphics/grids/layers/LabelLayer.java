package graphics.grids.layers;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;

import java.util.TreeSet;

/**
 * Created by Zuzka on 11.1.2016.
 */
public class LabelLayer extends GridPane {

    int size;
    Mode mode;
    Label[][] labels = new Label[9][9];

    public LabelLayer(int size) {
        this.size = size;
        this.mode = Mode.GIVENS;
        super.setHgap(1);
        super.setVgap(1);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Label l = new Label();
                l.setPrefWidth(size);
                l.setPrefHeight(size);
                l.setAlignment(Pos.CENTER);
                l.setBackground(Background.EMPTY);
                labels[i][j] = l;
                super.add(l, j, i);
            }
        }

    }

    public void setText(int x, int y, String text) {
        labels[x][y].setText(text);
    }

    public void setText(int x, int y, TreeSet<Integer> set) {
        if (this.mode.equals(Mode.GIVENS) && set.size() == 1) {
            for ( Integer i = 0; i < 9; i++ ) {
                if (set.contains(i)) {
                    i++;
                    labels[x][y].setText(i.toString());
                    break;
                }
            }
        }
        //zahrna aj pripad prazdneho textu
        else {
            StringBuffer s = new StringBuffer();
            for ( Integer i = 0; i < 9; i++ ) {
                if (set.contains(i)) {
                    s.append(i+1);
                }
            }
            labels[x][y].setText(new String(s));
        }
    }

    public Label[][] getLabels() {
        return this.labels;
    }

    public Mode getMode() {
        return this.mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void updateGrid() {
        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {
                filterText(i,j);
            }
        }
    }

    private void filterText(int x, int y) {
        Label text = labels[x][y];
        StringBuffer s = new StringBuffer();
        for (Integer i = 1; i <= 9; i++) {
            if (text.getText().contains(i.toString())) {
                s.append(i.toString());
            }
        }
        if (this.mode.equals(Mode.GIVENS) && (s.length()) > 1){
            text.setText("");
        } else {
            text.setText(new String(s));
        }
    }
}
