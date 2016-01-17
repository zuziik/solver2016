package commands;

import main.Sudoku;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class InputToSudokuCommand implements Command {

    Sudoku sudoku;

    public InputToSudokuCommand(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    /**
     * Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou
     */
    @Override
    public void execute() {
        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {
                Set<Integer> options = sudoku.getOptions().get(i).get(j);
                options.clear();
                Set<Integer> new_options = sudoku.getInputGrid().getOptions(i,j);
                for ( Integer x : new_options ) {
                    options.add(x);
                }
            }
        }

    }
}