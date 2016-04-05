package graphics.grids;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * Trieda definuje rozhranie pre mriezku sudoku
 */
public abstract class Grid extends StackPane {
    final int size = 40;

    public void alignNode(Node node) {
        super.setAlignment(node, Pos.CENTER);
    }

}