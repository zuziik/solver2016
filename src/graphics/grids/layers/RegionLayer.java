package graphics.grids.layers;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;

/**
 * Created by Zuzka on 10.1.2016.
 */
public class RegionLayer extends GridPane {

    int size;
    Rectangle cells[][] = new Rectangle[9][9];
    HashMap<Character,Color> color;

    public RegionLayer(int size) {
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
        this.color = new HashMap<>(4);
        color.put('A', Color.RED);
        color.put('B', Color.BLUE);
        color.put('C', Color.GREEN);
        color.put('D', Color.YELLOW);
    }

    public void color(int x, int y, char c) {
        cells[x][y].setFill(color.get(Character.toUpperCase(c)));
        cells[x][y].setOpacity(0.25);
    }

    public void setBlank(int x, int y) {
        cells[x][y].setOpacity(0);
    }

    public boolean isRegion(int x, int y, char c) {
        return cells[x][y].getFill().equals(color.get(c));
    }

    @Override
    public RegionLayer clone() {
        RegionLayer cloned = new RegionLayer(size);
        Rectangle[][] cloned_cells = new Rectangle[9][9];
        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {
                Rectangle c = new Rectangle(size, size);
                Rectangle old = cells[i][j];
                c.setFill(old.getFill());
                c.setStroke(old.getStroke());
                c.setOpacity(old.getOpacity());
                c.setStrokeWidth(old.getStrokeWidth());
                cloned.add(c, j, i);
                cloned_cells[i][j] = c;
            }
        }
        return cloned;
    }
}