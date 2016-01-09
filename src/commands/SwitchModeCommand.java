package commands;

import main.Sudoku;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class SwitchModeCommand implements Command {

    Sudoku sudoku;

    public SwitchModeCommand(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    /**
     * Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou
     */
    @Override
    public void execute() {

    }

    /** Funkcia vrati text, ktory sa ma napisat na tlacidlo, pripadne polozku menu, o tom, na aky mod sa da zmenit
     * (teda opacny oproti sucasnemu)*/
    public String getText(){
        return null;
    }
}
