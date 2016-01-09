package graphics.stages;

import graphics.grids.InputGrid;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class SettingsStage {
    BorderPane pane = new BorderPane();
    Scene scene = new Scene(pane);
    Stage stage = new Stage();
    InputGrid inputGrid;

    public SettingsStage() {
        stage.setScene(scene);
        inputGrid = new InputGrid();
        pane.setLeft(inputGrid);
    }

    public void show() {
        stage.show();
    }
}
