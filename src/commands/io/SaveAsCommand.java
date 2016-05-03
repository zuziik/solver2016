package commands.io;

import commands.Command;
import graphics.InfoBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sudoku.Sudoku;

import java.io.File;

/**
 * Trieda reprezentuje prikaz, ktory zabezpeci ulozenie sudoku do pouzivatelom vybraneho
 * sudoku
 */
public class SaveAsCommand implements Command {

    private final Sudoku sudoku;
    private final Stage stage;
    private final InfoBox infoBox;

    /**
     * @param sudoku odkaz na aktualne sudoku
     * @param stage odkaz na aktualne okno
     * @param infoBox odkaz na tabulu, na ktoru sa vypisuju spravy o priebehu programu
     */
    public SaveAsCommand(Sudoku sudoku, Stage stage, InfoBox infoBox) {
        this.sudoku = sudoku;
        this.stage = stage;
        this.infoBox = infoBox;
    }

    /**
     * Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou
     */
    @Override
    public void execute() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("files/sudoku"));
        fileChooser.setTitle("Save File As");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showSaveDialog(stage);
        if (selectedFile != null) {
            sudoku.setFile(selectedFile);
            Command command = new SaveCommand(sudoku, infoBox);
            command.execute();
        }
    }
}
