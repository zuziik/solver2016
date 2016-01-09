package graphics.grids;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * Created by Zuzka on 9.1.2016.
 */
public abstract class Grid extends StackPane {
    int cellSize = 40;
    int gridSize = cellSize*9;


    /** Funkcia zabezpeci obnovu zobrazovania na zaklade vybraneho modu (s vpiskami alebo bez nich) */

    public void refresh() {

    }
}
