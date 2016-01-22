package graphics.grids;

import graphics.grids.layers.*;

/**
 * Trieda reprezentuje vystupnu mriezku pre vystup generatora
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

    /** Funkcia nastavi text policku na pozicii x, y */
    public void setText(int x, int y, String text) {
        this.labelLayer.setText(x,y,text);
    }


}
