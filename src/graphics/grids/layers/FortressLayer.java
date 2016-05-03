package graphics.grids.layers;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;

/**
 * Trieda reprezentuje vrstvu so sedymi polickami pre pevnost
 */
public class FortressLayer extends GridPane {

    private final int size;
    private final Rectangle cells[][] = new Rectangle[9][9];
    private final Color color = Color.GRAY;

    /**
     * Konstruktor vytvori vrstvu na vykreslovanie policok v pevnosti.
     * @param size velkost jedneho policka v sudoku
     */
    public FortressLayer(int size) {
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
    }

    /** Funkcia nastavi policku na pozicii x, y region A-D
     * @param x x-ova suradnica policka
     * @param y y-ova suradnica policka
     */
    public void color(int x, int y) {
        cells[x][y].setFill(this.color);
        cells[x][y].setOpacity(0.25);
    }

    /** Funkcia zrusi prislusnost policka na pozicii x, y k akemukolvek regionu
     * @param x x-ova suradnica policka
     * @param y y-ova suradnica policka
     */
    public void setBlank(int x, int y) {
        cells[x][y].setOpacity(0);
    }

    /** Funkcia vrati true, ak policko na pozicii x, y je sucastou pevnosti
     * @param x x-ova suradnica policka
     * @param y y-ova suradnica policka
     * @return vrati true, ak policko na pozicii x, y je sucastou pevnosti
     */
    public boolean isFortress(int x, int y) {
        return cells[x][y].getFill().equals(this.color) && cells[x][y].getOpacity() == 0.25;
    }

    /** Funkcia vrati klon vrstvy zobrazujucej pevnost
     * @return vrati klon vrstvy zobrazujucej pevnost
     */
    @Override
    public FortressLayer clone() {
        FortressLayer cloned = new FortressLayer(size);
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