package commands;

import graphics.InfoBox;
import graphics.grids.InputGrid;
import graphics.grids.OutputGrid;
import sudoku.Sudoku;

/**
 * Created by Zuzka on 5.4.2016.
 */
public class OutputToInputCommand implements Command {
    private Sudoku sudoku;
    private final InfoBox infoBox;

    public OutputToInputCommand(Sudoku sudoku, InfoBox infoBox) {
        this.sudoku = sudoku;
        this.infoBox = infoBox;
    }

    @Override
    public void execute() {
        InputGrid inputGrid = this.sudoku.getInputGrid();
        OutputGrid outputGrid = this.sudoku.getOutputGrid();
        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {
                inputGrid.setText(i,j,outputGrid.getText(i, j));
            }
        }
        this.infoBox.addInfo("Numbers transfered");
    }
}
