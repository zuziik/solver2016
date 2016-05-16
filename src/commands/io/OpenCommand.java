package commands.io;

import commands.Command;
import graphics.InfoBox;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Trieda reprezentuje prikaz, ktory zabezpeci otvorenie sudoku z pouzivatelom vybraneho
 * suboru
 */
public class OpenCommand implements Command {

    private final Stage stage;
    private final InfoBox infoBox;

    /**
     * @param stage odkaz na aktualne okno
     * @param infoBox odkaz na tabulu, na ktoru sa vypisuju spravy o priebehu programu
     */
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
        String path = getClass().getResource("/files").getPath();
        //fileChooser.setInitialDirectory(new File(path));
        //fileChooser.setInitialDirectory(new File("files/sudoku"));
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
