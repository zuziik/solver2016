package graphics.grids.layers;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

/**
 * Created by Zuzka on 10.1.2016.
 */
public class DiagonalLayer extends Pane {
    private final int size;
    private final Line l1;
    private final Line l2;

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

    @Override
    public DiagonalLayer clone() {
        DiagonalLayer cloned = new DiagonalLayer(this.size);
        Line cl1 = new Line(l1.getStartX(), l1.getStartY(), l1.getEndX(), l1.getEndY());
        Line cl2 = new Line(l2.getStartX(), l2.getStartY(), l2.getEndX(), l2.getEndY());
        cl1.setOpacity(l1.getOpacity());
        cl2.setOpacity(l2.getOpacity());
        cloned.getChildren().addAll(cl1, cl2);
        return cloned;
    }
}
