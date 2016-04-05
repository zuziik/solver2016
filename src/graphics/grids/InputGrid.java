package graphics.grids;

import graphics.grids.layers.*;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Trieda reprezentuje vstupnu mriezku na zadavanie sudoku
 */
public class InputGrid extends Grid {

    private final IrregularLayer irregularLayer = new IrregularLayer(size);
    private final RegionLayer regionLayer = new RegionLayer(size);
    private final FortressLayer fortressLayer = new FortressLayer(size);
    private final ParityLayer parityLayer = new ParityLayer(size);
    private final BorderLayer borderLayer = new BorderLayer(3*size);
    private final ConsecutiveLayer consecutiveLayer = new ConsecutiveLayer(size/8);
    private final DiagonalLayer diagonalLayer = new DiagonalLayer(9*size);
    private final TextFieldLayer textFieldLayer = new TextFieldLayer(size, this);

    public InputGrid() {
        super.getChildren().addAll(irregularLayer, regionLayer, fortressLayer, parityLayer, borderLayer,
                consecutiveLayer, diagonalLayer, textFieldLayer);
        this.textFieldLayer.setSettingsHandlers();
        super.alignNode(consecutiveLayer);
    }

    /** Funkcia vrati vrstvu nepravidelnych regionov */
    public IrregularLayer getIrregularLayer() {
        return this.irregularLayer;
    }

    /** Funkcia vrati vrstvu hlavnych diagonal */
    public DiagonalLayer getDiagonalLayer() {
        return this.diagonalLayer;
    }

    /** Funkcia vrati vrstvu sedych policok pre pevnost */
    public FortressLayer getFortressLayer() {
        return this.fortressLayer;
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

    public ConsecutiveLayer getConsecutiveLayer() {
        return this.consecutiveLayer;
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

    public List<List<Integer>> getFortress() {
        List<List<Integer>> fortress = new ArrayList<>();
        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {
                if (this.fortressLayer.isFortress(i, j)) {
                    List<Integer> pair = new ArrayList<>();
                    pair.add(i);
                    pair.add(j);
                    fortress.add(pair);
                }
            }
        }
        return fortress;
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

    public void setText(int x, int y, String z) {
        this.textFieldLayer.setText(x,y,z);
    }

    /** Funkcia aktualizuje obsah mriezky */
    public void updateGrid() {
        this.textFieldLayer.updateGrid();
    }

    public StackPane printImage() {
        StackPane image = new StackPane();
        BorderLayer borderLayer = this.borderLayer.clone();
        DiagonalLayer diagonalLayer = this.diagonalLayer.clone();
        IrregularLayer irregularLayer = this.irregularLayer.clone();
        ParityLayer parityLayer = this.parityLayer.clone();
        RegionLayer regionLayer = this.regionLayer.clone();
        FortressLayer fortressLayer = this.fortressLayer.clone();
        ConsecutiveLayer consecutiveLayer = this.consecutiveLayer.clone();
        TextFieldLayer textFieldLayer = this.textFieldLayer.clone();
        image.getChildren().addAll(irregularLayer, regionLayer, fortressLayer, parityLayer, consecutiveLayer, diagonalLayer, textFieldLayer);
        if (borderLayer != null) {
            image.getChildren().add(borderLayer);
        }
        return image;
    }

}
