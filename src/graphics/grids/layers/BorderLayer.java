package graphics.grids.layers;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by Zuzka on 10.1.2016.
 */
public class BorderLayer extends GridPane {

    int size;
    Rectangle[][] borders = new Rectangle[3][3];

    public BorderLayer(int size) {
        this.size = size;
        for ( int i = 0; i < 3; i++ ) {
            for ( int j = 0; j < 3; j++ ) {
                borders[i][j] = new Rectangle(size,size);
                borders[i][j].setFill(null);
                borders[i][j].setStrokeWidth(3);
                borders[i][j].setStroke(Color.BLACK);
                super.add(borders[i][j], j, i);
            }
        }
    }

    public void showBorders() {
        for (Rectangle[] row : borders) {
            for (Rectangle r : row) {
                r.setOpacity(1);
            }
        }
    }

    public void hideBorders() {
        for (Rectangle[] row : borders) {
            for (Rectangle r : row) {
                r.setOpacity(0);
            }
        }
    }
}
