package commands;

import graphics.grids.layers.Mode;
import main.Sudoku;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class SwitchModeCommand implements Command {

    Sudoku sudoku;
    String text;

    public SwitchModeCommand(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    /**
     * Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou
     */
    @Override
    public void execute() {

        Mode mode = sudoku.getInputGrid().getTextFieldLayer().getMode();

        if (mode.equals(Mode.GIVENS)) {
            this.text = "Show Pencilmarks";
            sudoku.getInputGrid().getTextFieldLayer().setMode(Mode.PENCILMARKS);
            sudoku.getOutputGrid().getLabelLayer().setMode(Mode.PENCILMARKS);
            sudoku.getInputGrid().getTextFieldLayer().showPencilmarks();
            sudoku.getOutputGrid().getLabelLayer().showPencilmarks();
        }

        else {
            this.text = "Hide Pencilmarks";
            sudoku.getInputGrid().getTextFieldLayer().setMode(Mode.GIVENS);
            sudoku.getOutputGrid().getLabelLayer().setMode(Mode.GIVENS);
            sudoku.getInputGrid().getTextFieldLayer().hidePencilmarks();
            sudoku.getOutputGrid().getLabelLayer().hidePencilmarks();
        }
    }

    /** Funkcia vrati text, ktory sa ma napisat na tlacidlo, pripadne polozku menu, o tom, na aky mod sa da zmenit
     * (teda opacny oproti sucasnemu)*/
    public String getText(){
        return this.text;
    }
}
