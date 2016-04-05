package graphics.grids.layers;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;


/**
 * Trieda reprezentuje vrstvu s cislami
 */
public class LabelLayer extends GridPane {

    private final int size;
    private final Label[][] labels = new Label[9][9];

    public LabelLayer(int size) {
        this.size = size;
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

    /** Funkcia nastavi policku na pozicii x, y text s cislom alebo moznostami pre cislo */
    public void setText(int x, int y, String text) {
        Label label = labels[x][y];
        label.setText(text);
        if ( text.length() == 1 ) {
            label.setFont(new Font("Arial", 20));
        }
        else {
            label.setFont(new Font("Courier New", 10));
            label.setWrapText(true);
        }
    }

    public String getText(int x, int y) {
        return this.labels[x][y].getText();
    }
}
