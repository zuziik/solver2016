package commands;

import graphics.InfoBox;
import javafx.scene.control.Label;
import sudoku.Sudoku;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class ShowProgressCommand implements Command {

    Sudoku sudoku;
    InfoBox infoBox;

    public ShowProgressCommand(Sudoku sudoku, InfoBox infoBox) {
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
