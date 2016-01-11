package graphics.grids.layers;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import org.w3c.dom.css.Rect;

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

    @Override
    public BorderLayer clone() {
        BorderLayer cloned = new BorderLayer(this.size);
        Rectangle[][] cloned_borders = new Rectangle[3][3];
        for ( int i = 0; i < 3; i++ ) {
            for ( int j = 0; j < 3; j++ ) {
                Rectangle c = new Rectangle(size, size);
                Rectangle old = borders[i][j];
                c.setFill(old.getFill());
                c.setStroke(old.getStroke());
                c.setStrokeWidth(old.getStrokeWidth());
                c.setOpacity(old.getOpacity());
                cloned.add(c, j, i);
                cloned_borders[i][j] = c;
            }
        }
        return cloned;
    }
}
