package graphics.grids;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class InputGrid extends Grid {

    public InputGrid() {
        this.getChildren().add(new Rectangle(100,100, Color.BLUE));
    }
}
