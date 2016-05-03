package graphics.grids.layers;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;

/**
 * Trieda reprezentuje vrstvu s extra regionmi
 */
public class RegionLayer extends GridPane {

    private final int size;
    private final Rectangle cells[][] = new Rectangle[9][9];
    private HashMap<Character,Color> color;

    /**
     * Konstruktor vytvori vrstvu na farebne oznacovanie policok podla ich prislusnosti k extra regionu.
     * @param size velkost jedneho policka v sudoku
     */
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

    /** Funkcia definuje farby pre jednotlive extra regiony */
    private void fillColors() {
        this.color = new HashMap<>(4);
        color.put('A', Color.RED);
        color.put('B', Color.BLUE);
        color.put('C', Color.GREEN);
        color.put('D', Color.YELLOW);
    }

    /** Funkcia nastavi policku na pozicii x, y region A-D
     * @param x x-ova suradnica policka
     * @param y y-ova suradnica policka
     * @param c znak oznacujuci, k akemu regionu policko patri (A-D)
     */
    public void color(int x, int y, char c) {
        cells[x][y].setFill(color.get(Character.toUpperCase(c)));
        cells[x][y].setOpacity(0.25);
    }

    /** Funkcia zrusi prislusnost policka na pozicii x, y k akemukolvek regionu
     * @param x x-ova suradnica policka
     * @param y y-ova suradnica policka
     */
    public void setBlank(int x, int y) {
        cells[x][y].setOpacity(0);
    }

    /** Funkcia vrati true, ak policko na pozicii x, y patri regionu c
     * @param x x-ova suradnica policka
     * @param y y-ova suradnica policka
     * @param c znak oznacujuci region(A-D)
     */
    public boolean isRegion(int x, int y, char c) {
        return cells[x][y].getFill().equals(color.get(c)) && cells[x][y].getOpacity() == 0.25;
    }

    /** Funkcia vrati klon vrstvy s extra regionmi
     * @return klon vrstvy s extra regionmi
     */
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