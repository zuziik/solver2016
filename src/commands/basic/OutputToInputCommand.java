package commands.basic;

import commands.Command;
import graphics.InfoBox;
import graphics.grids.InputGrid;
import graphics.grids.OutputGrid;
import sudoku.Sudoku;

/**
 * Trieda reprezentuje prikaz, ktory prepise vsetky cisla z vystupnej mriezky do vstupnej.
 */
public class OutputToInputCommand implements Command {
    private Sudoku sudoku;
    private final InfoBox infoBox;

    /**
     * @param sudoku aktualne sudoku
     * @param infoBox tabula, na ktoru sa vypisuju hlasky o cinnosti
     */
    public OutputToInputCommand(Sudoku sudoku, InfoBox infoBox) {
        this.sudoku = sudoku;
        this.infoBox = infoBox;
    }

    /** Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou*/
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
