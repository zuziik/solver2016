package commands;

import graphics.grids.InputGrid;
import graphics.grids.OutputGrid;
import sudoku.Sudoku;

/**
 * Created by Zuzka on 5.4.2016.
 */
public class OutputToInputCommand implements Command {
    private Sudoku sudoku;

    public OutputToInputCommand(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    @Override
    public void execute() {
        InputGrid inputGrid = this.sudoku.getInputGrid();
        OutputGrid outputGrid = this.sudoku.getOutputGrid();
        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {
                inputGrid.setText(i,j,outputGrid.getText(i,j));
            }
        }
    }
}
