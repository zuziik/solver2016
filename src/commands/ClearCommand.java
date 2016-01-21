package commands;

import graphics.grids.InputGrid;
import graphics.grids.layers.TextFieldLayer;
import sudoku.Sudoku;
import javafx.scene.control.TextField;

/**
 * Created by Zuzka on 17.1.2016.
 */
public class ClearCommand implements Command {
    private final Sudoku sudoku;

    public ClearCommand(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    /**
     * Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou
     */
    @Override
    public void execute() {
        InputGrid inputGrid = sudoku.getInputGrid();
        TextFieldLayer textFieldLayer = inputGrid.getTextFieldLayer();
        TextField[][] textFields = textFieldLayer.getTextFields();
        for ( TextField[] row : textFields ) {
            for ( TextField text : row ) {
                text.setText("");
            }
        }
    }
}
