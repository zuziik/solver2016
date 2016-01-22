package commands;

import javafx.stage.Stage;

/**
 * Trieda reprezentuje prikaz, ktory zabezpeci zatvorenie vybraneho okna
 */
public class QuitCommand implements Command {

    private final Stage oldStage;

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
