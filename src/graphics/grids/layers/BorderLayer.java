package graphics.grids.layers;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Trieda reprezentuje vrstvu mriezky s okrajmi stvorcov 3x3 v mriezke
 */
public class BorderLayer extends GridPane {

    private final int size;
    private final Rectangle[][] borders = new Rectangle[3][3];

    /**
     * Konstruktor vytvori vrstvu na vykreslovanie okrajov 3x3 stvorcov v sudoku.
     * @param size velkost 3x3 stvorca v sudoku
     */
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

    /** Funkcia zobrazi okraje stvorcov 3x3 */
    public void showBorders() {
        for (Rectangle[] row : borders) {
            for (Rectangle r : row) {
                r.setOpacity(1);
            }
        }
    }

    /** Funkcia skryje okraje stvorcov 3x3 */
    public void hideBorders() {
        for (Rectangle[] row : borders) {
            for (Rectangle r : row) {
                r.setOpacity(0);
            }
        }
    }

    /** Funkcia vrati klon vrstvy s okrajmi mriezky
     * @return klon vrstvy s okrajmi mriezky
     */
    @Override
    public BorderLayer clone() {
        if (borders[0][0].getOpacity() == 0) {
            return null;
        }
        BorderLayer cloned = new BorderLayer(this.size);
        Rectangle[][] cloned_borders = new Rectangle[3][3];
        for ( int i = 0; i < 3; i++ ) {
            for ( int j = 0; j < 3; j++ ) {
                Rectangle c = new Rectangle(size, size);
                Rectangle old = borders[i][j];
                c.setFill(old.getFill());
                c.setStroke(old.getStroke());
                c.setStrokeWidth(old.getStrokeWidth());
                cloned.add(c, j, i);
                cloned_borders[i][j] = c;
            }
        }
        return cloned;
    }
}
