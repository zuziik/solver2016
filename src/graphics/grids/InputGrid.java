package graphics.grids;

import graphics.grids.layers.*;


/**
 * Created by Zuzka on 9.1.2016.
 */
public class InputGrid extends Grid {

    IrregularLayer irregularLayer = new IrregularLayer(size);
    RegionLayer regionLayer = new RegionLayer(size);
    ParityLayer parityLayer = new ParityLayer(size);
    BorderLayer borderLayer = new BorderLayer(3*size);
    TextFieldLayer textFieldLayer = new TextFieldLayer(size);

    public InputGrid() {
        super.getChildren().addAll(irregularLayer, regionLayer, parityLayer, borderLayer, textFieldLayer);
    }
}
