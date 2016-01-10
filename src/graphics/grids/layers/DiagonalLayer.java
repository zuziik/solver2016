package graphics.grids.layers;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

/**
 * Created by Zuzka on 10.1.2016.
 */
public class DiagonalLayer extends Pane {
    int size;
    Line l1;
    Line l2;

    public DiagonalLayer(int size) {
        this.size = size;
        l1 = new Line(0,0,size+8,size+8);
        l2 = new Line(size+8,0,0,size+8);
        hideDiagonals();
        super.getChildren().addAll(l1,l2);
    }

    public void showDiagonals() {
        l1.setOpacity(1);
        l2.setOpacity(1);
    }

    public void hideDiagonals() {
        l1.setOpacity(0);
        l2.setOpacity(0);
    }
}
