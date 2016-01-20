package graphics.stages;

import graphics.ToolBar;
import graphics.UpperMenu;
import graphics.grids.InputGrid;
import graphics.grids.OutputGrid;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sudoku.Sudoku;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class MainStage {

    Sudoku sudoku;
    UpperMenu menu;
    BorderPane pane = new BorderPane();
    ToolBar toolBar;
    InputGrid inputGrid;
    OutputGrid outputGrid;
    Stage stage = new Stage();
    Scene scene = new Scene(pane);
    Label infoBox;

    public MainStage(Sudoku sudoku){
        this.sudoku = sudoku;
        this.infoBox = new Label("#Solutions: ???");
        this.toolBar = new ToolBar(stage, this.sudoku, infoBox);
        this.menu = new UpperMenu(stage, this.sudoku, infoBox);
        this.inputGrid = sudoku.getInputGrid();
        this.outputGrid = sudoku.getOutputGrid();
        this.pane.setLeft(inputGrid);
        this.pane.setRight(outputGrid);
        this.pane.setCenter(toolBar);
        this.pane.setTop(menu);
    }

    public void start(){
        this.stage.setScene(scene);
        this.stage.setTitle("SUDOKU Solution Checker");
        this.stage.show();
    }
}
