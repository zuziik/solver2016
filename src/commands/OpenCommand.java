package commands;

import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sudoku.Sudoku;
import sudoku.Type;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class OpenCommand implements Command {

    private final Stage stage;

    public OpenCommand(Stage stage){
        this.stage = stage;
    }

    /**
     * Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou
    */
    @Override
    public void execute() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("files/sudoku"));
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(
                new ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            Command command = new ReloadCommand(stage, selectedFile);
            command.execute();
        }
    }
}
