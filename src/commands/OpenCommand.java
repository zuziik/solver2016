package commands;

import graphics.InfoBox;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sudoku.Sudoku;
import sudoku.Type;

import javax.sound.midi.MidiDevice;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * Trieda reprezentuje prikaz, ktory zabezpeci otvorenie sudoku z pouzivatelom vybraneho
 * suboru
 */
public class OpenCommand implements Command {

    private final Stage stage;
    private final InfoBox infoBox;

    public OpenCommand(Stage stage, InfoBox infoBox){
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
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(
                new ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            Command command = new ReloadCommand(stage, selectedFile, infoBox);
            command.execute();
        }
    }
}
