package graphics.grids;

import graphics.grids.layers.*;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class OutputGrid extends Grid {
    private final LabelLayer labelLayer;

    public OutputGrid(InputGrid inputGrid) {
        IrregularLayer irregularLayer = inputGrid.getIrregularLayer().clone();
        RegionLayer regionLayer = inputGrid.getRegionLayer().clone();
        ParityLayer parityLayer = inputGrid.getParityLayer().clone();
        BorderLayer borderLayer = inputGrid.getBorderLayer().clone();
        DiagonalLayer diagonalLayer = inputGrid.getDiagonalLayer().clone();

        this.labelLayer = new LabelLayer(size);
        super.getChildren().addAll(irregularLayer, regionLayer, parityLayer, diagonalLayer, labelLayer);
        if (borderLayer != null){
            super.getChildren().add(borderLayer);
        }
    }

    public LabelLayer getLabelLayer() {
        return this.labelLayer;
    }

    public void setText(int x, int y, String text) {
        this.labelLayer.setText(x,y,text);
    }


}
