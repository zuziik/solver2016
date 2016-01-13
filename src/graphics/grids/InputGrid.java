package graphics.grids;

import graphics.grids.layers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Created by Zuzka on 9.1.2016.
 */
public class InputGrid extends Grid {

    private IrregularLayer irregularLayer = new IrregularLayer(size);
    private RegionLayer regionLayer = new RegionLayer(size);
    private ParityLayer parityLayer = new ParityLayer(size);
    private BorderLayer borderLayer = new BorderLayer(3*size);
    private DiagonalLayer diagonalLayer = new DiagonalLayer(9*size);
    private TextFieldLayer textFieldLayer = new TextFieldLayer(size, this);

    public InputGrid() {
        super.getChildren().addAll(irregularLayer, regionLayer, parityLayer, borderLayer, diagonalLayer, textFieldLayer);
        this.textFieldLayer.setSettingsHandlers();
    }

    public IrregularLayer getIrregularLayer() {
        return this.irregularLayer;
    }

    public DiagonalLayer getDiagonalLayer() {
        return this.diagonalLayer;
    }

    public RegionLayer getRegionLayer() {
        return this.regionLayer;
    }

    public ParityLayer getParityLayer() {
        return this.parityLayer;
    }

    public BorderLayer getBorderLayer() {
        return this.borderLayer;
    }

    public TextFieldLayer getTextFieldLayer() {
        return this.textFieldLayer;
    }

    public List<List<Integer>> getEven() {
        List<List<Integer>> evens = new ArrayList<>();
        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {
                if (this.parityLayer.isEven(i,j)) {
                    List<Integer> pair = new ArrayList<>();
                    pair.add(i);
                    pair.add(j);
                    evens.add(pair);
                }
            }
        }
        return evens;
    }

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

    //zahrna irregularne aj extra regiony
    public List<List<List<Integer>>> getRegions() {
        List<List<List<Integer>>> regions = new ArrayList<>();
        for ( int x = 1; x <= 9; x++ ) {
            List<List<Integer>> region = new ArrayList<>();
            for ( int i = 0; i < 9; i++ ) {
                for ( int j = 0; j < 9; j++ ) {
                    if (this.irregularLayer.isRegion(i,j,x)) {
                        List<Integer> pair = new ArrayList<>();
                        pair.add(i);
                        pair.add(j);
                        region.add(pair);
                    }
                }
            }
            if (region.size() > 0) {
                regions.add(region);
            }
        }
        for ( char x = 'A'; x <= 'D'; x++ ) {
            List<List<Integer>> region = new ArrayList<>();
            for ( int i = 0; i < 9; i++ ) {
                for ( int j = 0; j < 9; j++ ) {
                    if (this.regionLayer.isRegion(i,j,x)) {
                        List<Integer> pair = new ArrayList<>();
                        pair.add(i);
                        pair.add(j);
                        region.add(pair);
                    }
                }
            }
            if (region.size() > 0) {
                regions.add(region);
            }
        }

        return regions;
    }

    public void clear() {
        this.textFieldLayer.clear();
    }

    public Set<Integer> getOptions(int x, int y) {
        return this.textFieldLayer.getOptions(x,y);
    }
}
