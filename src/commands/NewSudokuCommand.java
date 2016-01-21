package commands;

import graphics.stages.ConfigurationStage;
import javafx.stage.Stage;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class NewSudokuCommand implements Command {

    private final Stage oldStage;

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
