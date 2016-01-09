package graphics.stages;

import generators.Generator;
import graphics.ToolBar;
import graphics.UpperMenu;
import graphics.grids.InputGrid;
import graphics.grids.OutputGrid;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Sudoku;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class MainStage {

    Sudoku sudoku;
    UpperMenu menu;
    BorderPane pane = new BorderPane();
    ToolBar toolBar = new ToolBar();
    InputGrid inputGrid = new InputGrid();
    OutputGrid outputGrid = new OutputGrid();
    Stage stage = new Stage();
    Scene scene = new Scene(pane);

    public MainStage(Sudoku sudoku){
        this.sudoku = sudoku;
        this.menu = new UpperMenu(stage, sudoku);
    }

    public void start(){
        this.stage.setScene(scene);
        this.stage.setTitle("SUDOKU Solution Checker");
        this.stage.show();
    }
}
