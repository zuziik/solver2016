package commands;

import javafx.scene.control.Label;
import main.Sudoku;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class CountSolutionsCommand implements Command {

    Label infoBox;
    Sudoku sudoku;

    public CountSolutionsCommand(Sudoku sudoku, Label infoBox) {
        this.sudoku = sudoku;
        this.infoBox = infoBox;
    }

    /**
     * Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou
     */
    @Override
    public void execute() {

    }
}