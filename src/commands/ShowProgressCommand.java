package commands;

import sudoku.Sudoku;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class ShowProgressCommand implements Command {

    Sudoku sudoku;

    public ShowProgressCommand(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    /**
     * Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou
     */
    @Override
    public void execute() {

    }
}
