package commands;

import sudoku.Sudoku;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class UpdateCommand implements Command {

    private final Sudoku sudoku;

    public UpdateCommand(Sudoku sudoku) {
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
                options.addAll(new_options.stream().collect(Collectors.toList()));
                sudoku.getOutputGrid().getLabelLayer().getLabels()[i][j].setText(
                        sudoku.getInputGrid().getTextFieldLayer().getTextFields()[i][j].getText());
            }
        }

    }
}
