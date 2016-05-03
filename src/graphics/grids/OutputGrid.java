package graphics.grids;

import graphics.grids.layers.*;

/**
 * Trieda reprezentuje vystupnu mriezku pre vystup generatora
 */
public class OutputGrid extends Grid {
    private final LabelLayer labelLayer;
    private ConsecutiveLayer consecutiveLayer;

    /**
     * Konstruktor vytvori vystupnu mriezku so vsetkymi vrstvami naklonovanymi podla vstupnej mriezky
     * @param inputGrid odkaz na vstupnu mriezku, z ktorej bude naklonovanych vacsina vrstiev
     */
    public OutputGrid(InputGrid inputGrid) {
        IrregularLayer irregularLayer = inputGrid.getIrregularLayer().clone();
        RegionLayer regionLayer = inputGrid.getRegionLayer().clone();
        ParityLayer parityLayer = inputGrid.getParityLayer().clone();
        BorderLayer borderLayer = inputGrid.getBorderLayer().clone();
        DiagonalLayer diagonalLayer = inputGrid.getDiagonalLayer().clone();
        FortressLayer fortressLayer = inputGrid.getFortressLayer().clone();
        consecutiveLayer = inputGrid.getConsecutiveLayer().clone();

        this.labelLayer = new LabelLayer(size);
        super.getChildren().addAll(irregularLayer, regionLayer, fortressLayer, parityLayer);
        if (borderLayer != null){
            super.getChildren().add(borderLayer);
        }
        super.getChildren().addAll(consecutiveLayer, diagonalLayer, labelLayer);
    }

    /** Funkcia nastavi text policku na pozicii x, y
     * @param x x-ova suradnica policka
     * @param y y-ova suradnica policka
     * @param text text, ktory sa ma zapisat do policka na pozicii x,y
     */
    public void setText(int x, int y, String text) {
        this.labelLayer.setText(x, y, text);
    }

    /**
     * Funkcia vrati text z policka na suradniciach x,y
     * @param x x-ova suradnica policka
     * @param y y-ova suradnica policka
     * @return vrati text v policku na suradniciach x,y
     */
    public String getText(int x, int y) {
        return this.labelLayer.getText(x,y);
    }

    /**
     * Funkcia nastavi vystupnej mriezke vrstvu so suslednymi bodkami
     * @param consecutiveLayer odkaz na vrstvu so suslednymi bodkami
     */
    public void setConsecutiveLayer(ConsecutiveLayer consecutiveLayer) {
        this.consecutiveLayer = consecutiveLayer;
        super.getChildren().add(consecutiveLayer);
    }

    /**
     * Funkcia odstrani vystupnej mriezke odkaz na vrstvu so suslednymi bodkami
     */
    public void removeConsecutiveLayer() {
        super.getChildren().remove(consecutiveLayer);
    }
}
