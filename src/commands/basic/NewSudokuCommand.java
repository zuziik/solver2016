package commands.basic;

import commands.Command;
import graphics.stages.ConfigurationStage;
import javafx.stage.Stage;

/**
 * Trieda reprezentuje prikaz, ktory zabezpeci otvorenie okna na nakonfigurovanie sudoku
 */
public class NewSudokuCommand implements Command {

    private final Stage oldStage;

    /**
     * @param stage odkaz na aktualne okno, ktore sa ma zamenit za pracovne okno so sudoku
     */
    public NewSudokuCommand(Stage stage) {
        this.oldStage = stage;
    }

    /**
     * Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou
     */
    @Override
    public void execute() {
        oldStage.close();
        ConfigurationStage configurationStage = new ConfigurationStage();
        configurationStage.show();
    }
}
