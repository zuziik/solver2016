package graphics.grids;

import graphics.grids.layers.*;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class OutputGrid extends Grid {
    private IrregularLayer irregularLayer;
    private RegionLayer regionLayer;
    private ParityLayer parityLayer;
    private BorderLayer borderLayer;
    private DiagonalLayer diagonalLayer;
    private LabelLayer labelLayer;

    public OutputGrid(IrregularLayer irregularLayer, RegionLayer regionLayer, ParityLayer parityLayer,
                      BorderLayer borderLayer, DiagonalLayer diagonalLayer) {
        this.irregularLayer = irregularLayer.clone();
        this.regionLayer = regionLayer.clone();
        this.parityLayer = parityLayer.clone();
        this.borderLayer = borderLayer.clone();
        this.diagonalLayer = diagonalLayer.clone();
        this.labelLayer = new LabelLayer(size);
    }
}
