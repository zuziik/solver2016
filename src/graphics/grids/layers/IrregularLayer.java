package graphics.grids.layers;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class IrregularLayer extends GridPane {

    private final int size;
    private final Rectangle cells[][] = new Rectangle[9][9];
    private HashMap<Integer,Color> color;

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

    public boolean isRegion(int x, int y, int number) {
        return cells[x][y].getFill().equals(color.get(number));
    }

    @Override
    public IrregularLayer clone() {
        IrregularLayer cloned = new IrregularLayer(size);
        Rectangle[][] cloned_cells = new Rectangle[9][9];
        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {
                Rectangle c = new Rectangle(size, size);
                Rectangle old = cells[i][j];
                c.setFill(old.getFill());
                c.setStroke(old.getStroke());
                c.setStrokeWidth(old.getStrokeWidth());
                cloned.add(c, j, i);
                cloned_cells[i][j] = c;
            }
        }
        return cloned;
    }
}
