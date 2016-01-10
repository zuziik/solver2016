package graphics.grids.layers;

import graphics.grids.Grid;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;

/**
 * Created by Zuzka on 10.1.2016.
 */
public class ParityLayer extends GridPane {

    int radius;
    Circle cells[][] = new Circle[9][9];
    HashMap<Character,Color> color;

    public ParityLayer(int size) {
        this.radius = size/2;
        fillColors();

        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ){
                cells[i][j] = new Circle(radius);
                cells[i][j].setFill(null);
                cells[i][j].setStroke(Color.WHITE);
                setBlank(i, j);
                super.add(cells[i][j], j, i);
            }
        }

        fillColors();
    }

    private void fillColors() {
        this.color = new HashMap<>(2);
        color.put('O', Color.RED);
        color.put('E', Color.BLUE);
    }

    public void color(int x, int y, char c) {
        cells[x][y].setOpacity(1);
        cells[x][y].setStroke(color.get(Character.toUpperCase(c)));
    }

    public void setBlank(int x, int y) {
        cells[x][y].setOpacity(0);
    }

}
