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

    public OutputGrid(InputGrid inputGrid) {
        this.irregularLayer = inputGrid.getIrregularLayer().clone();
        this.regionLayer = inputGrid.getRegionLayer().clone();
        this.parityLayer = inputGrid.getParityLayer().clone();
        this.borderLayer = inputGrid.getBorderLayer().clone();
        this.diagonalLayer = inputGrid.getDiagonalLayer().clone();
        this.labelLayer = new LabelLayer(size);
        super.getChildren().addAll(irregularLayer, regionLayer, parityLayer, diagonalLayer, labelLayer);
        if (borderLayer != null){
            super.getChildren().add(borderLayer);
        }
    }

    public LabelLayer getLabelLayer() {
        return this.labelLayer;
    }


}
