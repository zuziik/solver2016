package commands;

import main.Sudoku;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class SaveCommand implements Command {

    Sudoku sudoku;

    public SaveCommand(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    /**
     * Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou
     */
    @Override
    public void execute() {

    }
}
