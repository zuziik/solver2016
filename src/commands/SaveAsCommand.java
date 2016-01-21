package commands;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sudoku.Sudoku;

import java.io.File;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class SaveAsCommand implements Command {

    private final Sudoku sudoku;
    private final Stage stage;

    public SaveAsCommand(Sudoku sudoku, Stage stage) {
        this.sudoku = sudoku;
        this.stage = stage;
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
            Command command = new SaveCommand(sudoku);
            command.execute();
        }
    }
}
