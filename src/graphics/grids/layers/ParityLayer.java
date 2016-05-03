package graphics.grids.layers;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.HashMap;

/**
 * Trieda reprezentuje vrstvu s parnymi/neparnymi polickami
 */
public class ParityLayer extends GridPane {

    private final int radius;
    private final int size;
    private Circle cells[][] = new Circle[9][9];
    private HashMap<Character,Color> color;

    /**
     * Konstruktor vytvori vrstvu na vykreslovanie policok s parnymi a neparnymi cislami v sudoku.
     * @param size velkost jedneho policka v sudoku
     */
    public ParityLayer(int size) {
        this.size = size;
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

    /** Funkcia definuje farby pre parne a neparne policka */
    private void fillColors() {
        this.color = new HashMap<>(2);
        color.put('O', Color.RED);
        color.put('E', Color.BLUE);
    }

    /** Funkcia nastavi policko na pozicii x, y na parne, resp. neparne
     * @param x x-ova suradnica policka
     * @param y y-ova suradnica policka
     * @param c znak oznacujuci, ci ma byt policko parne (e/E) alebo neparne (o/O)
     */
    public void color(int x, int y, char c) {
        cells[x][y].setOpacity(1);
        cells[x][y].setStroke(color.get(Character.toUpperCase(c)));
    }

    /** Funkcia zrusi policku na pozicii x, y akukolvek prislusnost k parite
     * @param x x-ova suradnica policka
     * @param y y-ova suradnica policka
     */
    public void setBlank(int x, int y) {
        cells[x][y].setOpacity(0);
    }

    /** Funkcia vrati true, ak je policko na pozicii x, y parne
     * @param x x-ova suradnica policka
     * @param y y-ova suradnica policka
     * @return true, ak je policko na pozicii x, y parne
     */
    public boolean isEven(int x, int y) {
        return cells[x][y].getStroke().equals(color.get('E')) && cells[x][y].getOpacity() == 1;
    }

    /** Funkcia vrati true, ak je policko na pozicii x, y neparne
     * @param x x-ova suradnica policka
     * @param y y-ova suradnica policka
     * @return true, ak je policko na pozicii x, y neparne
     */
    public boolean isOdd(int x, int y) {
        return cells[x][y].getStroke().equals(color.get('O')) && cells[x][y].getOpacity() == 1;
    }

    /** Funkcia vrati klon vrstvy s parnymi a neparnymi polickami
     * @return klon vrstvy s parnymi a neparnymi polickami
     */
    @Override
    public ParityLayer clone() {
        ParityLayer cloned = new ParityLayer(size);
        Circle[][] cloned_cells = new Circle[9][9];
        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {
                Circle c = new Circle(radius);
                Circle old = cells[i][j];
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