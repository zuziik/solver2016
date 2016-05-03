package commands.basic;

import commands.Command;
import javafx.stage.Stage;

/**
 * Trieda reprezentuje prikaz, ktory zabezpeci zatvorenie vybraneho okna
 */
public class QuitCommand implements Command {

    private final Stage oldStage;

    /**
     * @param stage odkaz na aktualne okno, ktore sa ma zavriet
     */
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
