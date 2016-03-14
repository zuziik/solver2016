package graphics.stages;

import graphics.InfoBox;
import graphics.ToolBar;
import graphics.UpperMenu;
import graphics.grids.InputGrid;
import graphics.grids.OutputGrid;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sudoku.Sudoku;
import sudoku.Type;

import java.util.Set;

/**
 * Trieda reprezentuje hlavne okno aplikacie, v ktorom pouzivatel generuje sudoku alebo kontroluje jednoznacnost jeho
 * riesenia
 */
public class MainStage {

    private final Sudoku sudoku;
    private final UpperMenu menu;
    private final BorderPane pane = new BorderPane();
    private final ToolBar toolBar;
    private final InputGrid inputGrid;
    private final OutputGrid outputGrid;
    private final Stage stage = new Stage();
    private final Scene scene = new Scene(pane);
    private final InfoBox infoBox;
    private final Label types;

    public MainStage(Sudoku sudoku){
        this.sudoku = sudoku;
        this.infoBox = new InfoBox();
        this.sudoku.setInfoBox(infoBox);
        this.toolBar = new ToolBar(stage, sudoku, infoBox);
        this.menu = new UpperMenu(stage, this.sudoku, infoBox);
        this.inputGrid = sudoku.getInputGrid();
        this.outputGrid = sudoku.getOutputGrid();
        this.pane.setLeft(inputGrid);
        this.pane.setRight(outputGrid);
        this.pane.setCenter(toolBar);
        this.pane.setTop(menu);
        this.types = new Label();
        this.types.setText(this.sudoku.getTypesText());
        this.types.setTextFill(Color.BLUE);
        this.pane.setBottom(types);
    }

    /** Funkcia zabezpeci zobrazenie hlavneho okna */
    public void start(){
        this.stage.setScene(scene);
        this.stage.setTitle("SUDOKU Solution Checker");
        this.inputGrid.getTextFieldLayer().updateGrid();
        this.stage.show();
    }
}
