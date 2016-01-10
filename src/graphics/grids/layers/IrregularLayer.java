package graphics.grids.layers;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class IrregularLayer extends GridPane {

    int size;
    Rectangle cells[][] = new Rectangle[9][9];
    HashMap<Integer,Color> color;

    public IrregularLayer(int size) {
        this.size = size;

        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ){
                cells[i][j] = new Rectangle(size,size);
                cells[i][j].setStroke(Color.BLACK);
                cells[i][j].setStrokeWidth(0.25);
                setBlank(i,j);
                super.add(cells[i][j], j, i);
            }
        }

        fillColors();
    }

    private void fillColors() {
        this.color = new HashMap<>(9);
        color.put(1, Color.RED);
        color.put(2, Color.BLUE);
        color.put(3, Color.PURPLE);
        color.put(4, Color.YELLOW);
        color.put(5, Color.BROWN);
        color.put(6, Color.GREEN);
        color.put(7, Color.GRAY);
        color.put(8, Color.PINK);
        color.put(9, Color.ORANGE);
    }

    public void color(int x, int y, int i) {
        cells[x][y].setFill(color.get(i));
    }

    public void setBlank(int x, int y) {
        cells[x][y].setFill(Color.WHITE);
    }
}