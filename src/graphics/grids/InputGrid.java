package graphics.grids;

import graphics.grids.layers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Trieda reprezentuje vstupnu mriezku na zadavanie sudoku
 */
public class InputGrid extends Grid {

    private final IrregularLayer irregularLayer = new IrregularLayer(size);
    private final RegionLayer regionLayer = new RegionLayer(size);
    private final ParityLayer parityLayer = new ParityLayer(size);
    private final BorderLayer borderLayer = new BorderLayer(3*size);
    private final DiagonalLayer diagonalLayer = new DiagonalLayer(9*size);
    private final TextFieldLayer textFieldLayer = new TextFieldLayer(size, this);

    public InputGrid() {
        super.getChildren().addAll(irregularLayer, regionLayer, parityLayer, borderLayer, diagonalLayer, textFieldLayer);
        this.textFieldLayer.setSettingsHandlers();
    }

    /** Funkcia vrati vrstvu nepravidelnych regionov */
    public IrregularLayer getIrregularLayer() {
        return this.irregularLayer;
    }

    /** Funkcia vrati vrstvu hlavnych diagonal */
    public DiagonalLayer getDiagonalLayer() {
        return this.diagonalLayer;
    }

    /** Funkcia vrati vrstvu extra regionov */
    public RegionLayer getRegionLayer() {
        return this.regionLayer;
    }

    /** Funkcia vrati paritnu vrstvu */
    public ParityLayer getParityLayer() {
        return this.parityLayer;
    }

    /** Funkcia vrati vrstvu okrajov 3x3 stvorcov */
    public BorderLayer getBorderLayer() {
        return this.borderLayer;
    }

    /** Funkcia vrati vrstvu textovych poli */
    public TextFieldLayer getTextFieldLayer() {
        return this.textFieldLayer;
    }

    /** Funkcia vrati zoznam policok s parnymi cislami */
    public List<List<Integer>> getEven() {
        List<List<Integer>> evens = new ArrayList<>();
        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {
                if (this.parityLayer.isEven(i, j)) {
                    List<Integer> pair = new ArrayList<>();
                    pair.add(i);
                    pair.add(j);
                    evens.add(pair);
                }
            }
        }
        return evens;
    }

    /** Funkcia vrati zoznam policok s neparnymi cislami */
    public List<List<Integer>> getOdd() {
        List<List<Integer>> odds = new ArrayList<>();
        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {
                if (this.parityLayer.isOdd(i, j)) {
                    List<Integer> pair = new ArrayList<>();
                    pair.add(i);
                    pair.add(j);
                    odds.add(pair);
                }
            }
        }
        return odds;
    }

    /** Funkcia vrati zoznam nepravidelnych regionov */
    public List<List<List<Integer>>> getIrregulars() {
        List<List<List<Integer>>> regions = new ArrayList<>();
        boolean empty = true;
        for ( int x = 1; x <= 9; x++ ) {
            List<List<Integer>> region = new ArrayList<>();
            for ( int i = 0; i < 9; i++ ) {
                for ( int j = 0; j < 9; j++ ) {
                    if (this.irregularLayer.isRegion(i,j,x)) {
                        List<Integer> pair = new ArrayList<>();
                        pair.add(i);
                        pair.add(j);
                        region.add(pair);
                        empty = false;
                    }
                }
            }
            regions.add(region);
        }
        if (empty) {
            regions.clear();
        }
        return regions;
    }

    /** Funkcia vrati zoznam extra regionov */
    public List<List<List<Integer>>> getExtras() {
        List<List<List<Integer>>> regions = new ArrayList<>();
        boolean empty = true;
        for ( char x = 'A'; x <= 'D'; x++ ) {
            List<List<Integer>> region = new ArrayList<>();
            for ( int i = 0; i < 9; i++ ) {
                for ( int j = 0; j < 9; j++ ) {
                    if (this.regionLayer.isRegion(i,j,x)) {
                        List<Integer> pair = new ArrayList<>();
                        pair.add(i);
                        pair.add(j);
                        region.add(pair);
                        empty = false;
                    }
                }
            }
            regions.add(region);
        }
        if (empty) {
            regions.clear();
        }
        return regions;
    }

    /** Funkcia vrati moznosti pre policko na pozicii x, y */
    public Set<Integer> getOptions(int x, int y) {
        return this.textFieldLayer.getOptions(x, y);
    }

    /** Funkcia vrati text policka na pozicii x, y */
    public String getText(int x, int y) {
        return this.textFieldLayer.getText(x, y);
    }

    /** Funkcia aktualizuje obsah mriezky */
    public void updateGrid() {
        this.textFieldLayer.updateGrid();
    }


}
