package commands.basic;

import commands.Command;
import graphics.stages.SettingsStage;
import sudoku.Sudoku;

/**
 * Trieda reprezentuje prikaz, ktory nastavi SAT solveru casovy limit vypoctu
 */
public class SetTimeoutCommand implements Command {

    private final Sudoku sudoku;

    /**
     * @param sudoku odkaz na aktualne sudoku
     */
    public SetTimeoutCommand(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    /**
     * Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou
     */
    @Override
    public void execute() {
        SettingsStage settingsStage = new SettingsStage(sudoku);
        settingsStage.show();
    }
}
