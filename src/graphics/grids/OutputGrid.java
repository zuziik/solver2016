package graphics.grids;

import graphics.grids.layers.*;

/**
 * Trieda reprezentuje vystupnu mriezku pre vystup generatora
 */
public class OutputGrid extends Grid {
    private final LabelLayer labelLayer;
    private ConsecutiveLayer consecutiveLayer;

    public OutputGrid(InputGrid inputGrid) {
        IrregularLayer irregularLayer = inputGrid.getIrregularLayer().clone();
        RegionLayer regionLayer = inputGrid.getRegionLayer().clone();
        ParityLayer parityLayer = inputGrid.getParityLayer().clone();
        BorderLayer borderLayer = inputGrid.getBorderLayer().clone();
        DiagonalLayer diagonalLayer = inputGrid.getDiagonalLayer().clone();
        FortressLayer fortressLayer = inputGrid.getFortressLayer().clone();

        this.labelLayer = new LabelLayer(size);
        super.getChildren().addAll(irregularLayer, regionLayer, fortressLayer, parityLayer, diagonalLayer, labelLayer);
        if (borderLayer != null){
            super.getChildren().add(borderLayer);
        }
    }

    /** Funkcia nastavi text policku na pozicii x, y */
    public void setText(int x, int y, String text) {
        this.labelLayer.setText(x, y, text);
    }

    public String getText(int x, int y) {
        return this.labelLayer.getText(x,y);
    }

    public void addConsecutiveLayer(ConsecutiveLayer consecutiveLayer) {
        this.consecutiveLayer = consecutiveLayer;
        super.getChildren().add(consecutiveLayer);
    }

    public void removeConsecutiveLayer() {
        if (super.getChildren().contains(this.consecutiveLayer)) {
            super.getChildren().remove(this.consecutiveLayer);
        }
    }
}
