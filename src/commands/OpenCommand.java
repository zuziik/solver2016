package commands;

import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class OpenCommand implements Command {

    Stage stage;

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
            System.out.println("Hura!");
        }
    }
}
