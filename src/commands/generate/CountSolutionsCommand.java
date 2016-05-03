package commands.generate;

import commands.Command;
import commands.basic.InputToSudokuCommand;
import graphics.InfoBox;
import sudoku.Sudoku;

/**
 * Trieda reprezentuje prikaz, ktory spocita riesenia konkretneho sudoku
 */
public class CountSolutionsCommand implements Command {

    private final InfoBox infoBox;
    private final Sudoku sudoku;
    private int count;

    /**
     * @param sudoku aktualne sudoku
     * @param infoBox tabula, na ktoru sa vypisuju hlasky o cinnosti
     */
    public CountSolutionsCommand(Sudoku sudoku, InfoBox infoBox) {
        this.sudoku = sudoku;
        this.infoBox = infoBox;
    }

    /**
     * Funkcia vrati pocet rieseni sudoku
     * @return pocet rieseni aktualneho sudoku
     */
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
            text += "Time limit expired";
        }
        if (this.infoBox != null) this.infoBox.addInfo(text);
    }
}
