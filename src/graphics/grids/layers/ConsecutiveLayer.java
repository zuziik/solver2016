package graphics.grids.layers;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Zuzka on 5.4.2016.
 */
public class ConsecutiveLayer extends GridPane {
    private final int size;
    private final Circle cells[][] = new Circle[17][17];
    private final Rectangle padding;

    public ConsecutiveLayer(int size) {
        this.size = size;
        super.setHgap(9.49);
        super.setVgap(9.49);

        this.padding = new Rectangle(2*size, 2*size);
        padding.setOpacity(0);
        super.add(padding, 0, 0);

        for ( int i = 0; i < 17; i++ ) {
            for ( int j = 0; j < 17; j++ ){
                cells[i][j] = new Circle(size);
                cells[i][j].setStroke(Color.BLACK);
                cells[i][j].setStrokeWidth(0.25);
                cells[i][j].setFill(Color.WHITE);
                setBlank(i, j);
                super.add(cells[i][j], j+1, i+1);
            }
        }
    }

    public void showDot(int x1, int y1, int x2, int y2) {
        if (x1 == x2) {
            setColor(2 * x1, 2 * y1 + 1);
        }
        else if (y1 == y2) {
            setColor(2 * x1 + 1, 2 * y1);
        }
    }

    public void setColor(int x, int y) {
        this.cells[x][y].setOpacity(1);
    }

    public void setBlank(int x, int y) {
        this.cells[x][y].setOpacity(0);
    }

    public void hideAllDots() {
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                setBlank(i,j);
            }
        }
    }

    public boolean isConsecutive(int x1, int y1, int x2, int y2) {
        if ((x1 < 0) || (y1 < 0)) return false;
        if (x1 == x2) {
            return this.cells[2*x1][2*y1 + 1].getOpacity() == 1;
        }
        else if (y1 == y2) {
            return this.cells[2*x1 + 1][2*y1].getOpacity() == 1;
        }
        return false;
    }

    public List<List<Integer>> getDots() {
        List<List<Integer>> dots = new ArrayList<>();
        for ( int x = 0; x < 9; x++ ) {
            for ( int y = 0; y < 9; y++ ) {
                if ( isConsecutive(x-1, y, x, y) ) {
                    List<Integer> tuple = new ArrayList<>(Arrays.asList(x-1,y,x,y));
                    dots.add(tuple);
                }
                if ( isConsecutive(x, y-1, x, y )) {
                    List<Integer> tuple = new ArrayList<>(Arrays.asList(x,y-1,x,y));
                    dots.add(tuple);
                }
            }
        }
        return dots;
    }

    @Override
    public ConsecutiveLayer clone() {
        ConsecutiveLayer cloned = new ConsecutiveLayer(size);
        Circle[][] cloned_cells = new Circle[17][17];
        Rectangle cloned_padding = new Rectangle(2*size, 2*size);
        cloned_padding.setOpacity(0);
        cloned.add(cloned_padding, 0, 0);
        for ( int i = 0; i < 17; i++ ) {
            for ( int j = 0; j < 17; j++ ) {
                Circle c = new Circle(size);
                Circle old = cells[i][j];
                c.setFill(old.getFill());
                c.setStroke(old.getStroke());
                c.setOpacity(old.getOpacity());
                c.setStrokeWidth(old.getStrokeWidth());
                cloned.add(c, j+1, i+1);
                cloned_cells[i][j] = c;
            }
        }
        return cloned;
    }
}
