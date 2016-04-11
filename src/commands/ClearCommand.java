package commands;

import graphics.InfoBox;
import graphics.grids.InputGrid;
import graphics.grids.layers.TextFieldLayer;
import sudoku.Sudoku;
import javafx.scene.control.TextField;

/**
 * Trieda reprezentuje prikaz, ktory zabezpeci vymazanie cisel zo sudoku
 */
public class ClearCommand implements Command {
    private final Sudoku sudoku;
    private final InfoBox infoBox;

    public ClearCommand(Sudoku sudoku, InfoBox infoBox) {
        this.sudoku = sudoku;
        this.infoBox = infoBox;
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
        this.infoBox.addInfo("Grid cleared");
    }
}
