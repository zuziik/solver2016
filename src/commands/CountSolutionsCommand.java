package commands;

import graphics.InfoBox;
import sudoku.Sudoku;

import java.util.Date;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class CountSolutionsCommand implements Command {

    private final InfoBox infoBox;
    private final Sudoku sudoku;

    public CountSolutionsCommand(Sudoku sudoku, InfoBox infoBox) {
        this.sudoku = sudoku;
        this.infoBox = infoBox;
    }

    /**
     * Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou
     */
    @Override
    public void execute() {
        Command command = new InputToSudokuCommand(sudoku);
        command.execute();

        Date date = new Date();
        int count = sudoku.getGenerator().countSolutions();
        String text = date.toString().substring(11,19)+'\n';
        if (count >= 0) {
            text += "#Solutions: "+count+'\n';
        }
        else {
            text += "Time Limit Expired\n";
        }
        this.infoBox.addInfo(text);
    }
}
