package commands;

import graphics.InfoBox;
import sudoku.Sudoku;

import java.util.Date;

/**
 * Trieda reprezentuje prikaz, ktory spocita riesenia konkretneho sudoku
 */
public class CountSolutionsCommand implements Command {

    private final InfoBox infoBox;
    private final Sudoku sudoku;
    private int count;

    public CountSolutionsCommand(Sudoku sudoku, InfoBox infoBox) {
        this.sudoku = sudoku;
        this.infoBox = infoBox;
    }

    public int getCount() {
        return this.count;
    }

    /**
     * Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou. Pomocou generatora v sudoku
     * vypocita pocet jeho rieseni a vypise o tom spravu do infoBoxu
     */
    @Override
    public void execute() {
        Command command = new InputToSudokuCommand(sudoku);
        command.execute();

        this.count = sudoku.getGenerator().countSolutions();
        String text = "";

        if (count >= 0) {
            text += "#Solutions: "+count;
        }
        else {
            text += "Time Limit Expired";
        }
        if (this.infoBox != null) this.infoBox.addInfo(text);
    }
}
