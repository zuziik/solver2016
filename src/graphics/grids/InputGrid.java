package graphics.grids;

import graphics.grids.layers.*;


/**
 * Created by Zuzka on 9.1.2016.
 */
public class InputGrid extends Grid {

    private IrregularLayer irregularLayer = new IrregularLayer(size);
    private RegionLayer regionLayer = new RegionLayer(size);
    private ParityLayer parityLayer = new ParityLayer(size);
    private BorderLayer borderLayer = new BorderLayer(3*size);
    private TextFieldLayer textFieldLayer = new TextFieldLayer(size, this);

    public InputGrid() {
        super.getChildren().addAll(irregularLayer, regionLayer, parityLayer, borderLayer, textFieldLayer);
        this.textFieldLayer.setSettingsHandlers();
    }

    public IrregularLayer getIrregularLayer() {
        return this.irregularLayer;
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
}
