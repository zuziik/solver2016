package commands;

import graphics.stages.SettingsStage;
import javafx.stage.Stage;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class NewSudokuCommand implements Command {

    Stage oldStage;

    public NewSudokuCommand(Stage stage) {
        this.oldStage = stage;
    }

    /**
     * Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou
     */
    @Override
    public void execute() {
        oldStage.close();
        SettingsStage settingsStage = new SettingsStage();
        settingsStage.show();
    }
}
