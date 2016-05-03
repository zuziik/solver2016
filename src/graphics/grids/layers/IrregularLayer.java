package graphics.grids.layers;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;

/**
 * Trieda reprezentuje vrstvu s nepravidelnymi obrazcami
 */
public class IrregularLayer extends GridPane {

    private final int size;
    private final Rectangle cells[][] = new Rectangle[9][9];
    private HashMap<Integer,Color> color;

    /**
     * Konstruktor vytvori vrstvu na zafarbovanie policok v mriezke podla prislusnosti k nepravidelnemu regionu.
     * @param size velkost jedneho policka v sudoku
     */
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

    /** Funkcia zabezpeci definovanie farieb pre jednotlive regiony */
    private void fillColors() {
        this.color = new HashMap<>(9);
        color.put(1, Color.rgb(253,253,150));
        color.put(2, Color.rgb(255,193,204));
        color.put(3, Color.rgb(255,179,71));
        color.put(4, Color.rgb(255,105,97));
        color.put(5, Color.rgb(160,120,90));
        color.put(6, Color.rgb(207,207,196));
        color.put(7, Color.rgb(119,158,203));
        color.put(8, Color.rgb(69,206,162));
        color.put(9, Color.rgb(223, 255, 0));
    }

    /** Funkcia zafarbi policko na pozicii x, y farbou regionu i
     * @param x x-ova suradnica policka
     * @param y y-ova suradnica policka
     * @param i poradove cislo regionu
     */
    public void color(int x, int y, int i) {
        cells[x][y].setFill(color.get(i));
    }

    /** Funkcia zrusi policku na pozicii x, y akukolvek prislusnost k regionu
     * @param x x-ova suradnica policka
     * @param y y-ova suradnica policka
     */
    public void setBlank(int x, int y) {
        cells[x][y].setFill(Color.WHITE);
    }

    /** Funkcia vrati true, ak je policko na pozicii x, y sucastou regionu s cislom number
     * @param x x-ova suradnica policka
     * @param y y-ova suradnica policka
     * @return poradove cislo regionu
     */
    public boolean isRegion(int x, int y, int number) {
        return cells[x][y].getFill().equals(color.get(number));
    }

    /** Funkcia vrati klon vrstvy s nepravidelnymi regionmi
     * @return vrati klon vrstvy s nepravidelnymi regionmi
     * */
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
