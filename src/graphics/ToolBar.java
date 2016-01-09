package graphics;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class ToolBar extends VBox {
    Label infoBox = new Label("#Solutions: ???");
    Button update = new Button("Update Output");
    Button switchMode = new Button("Show Pencilmarks");
    Button countSolutions = new Button("Count Solutions");
    Button showSolution = new Button("ShowSolution");
    Button showProgress = new Button("ShowProgress");

    public ToolBar() {
        this.getChildren().addAll(infoBox, update, switchMode, countSolutions, showSolution, showProgress);
    }
}
