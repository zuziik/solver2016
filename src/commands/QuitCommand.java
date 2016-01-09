package commands;

import javafx.stage.Stage;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class QuitCommand implements Command {

    Stage oldStage;

    public QuitCommand(Stage stage) {
        this.oldStage = stage;
    }

    /**
     * Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou
     */
    @Override
    public void execute() {
        oldStage.close();
    }
}
