package commands.io;

import commands.Command;
import graphics.InfoBox;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Trieda reprezentuje prikaz, ktory vytvori screenshot aktualnej mriezky a ulozi ho do suboru
 */
public class ExportCommand implements Command {
    Node node;
    Stage stage;
    private final InfoBox infoBox;

    /**
     * @param node odkaz na aktualne sudoku ako graficky objekt
     * @param stage odkaz na aktualne okno
     * @param infoBox odkaz na tabulu, na ktoru sa vypisuju hlasky o cinnosti
     */
    public ExportCommand(Node node, Stage stage, InfoBox infoBox) {
        this.node = node;
        this.stage = stage;
        this.infoBox = infoBox;
    }

    /**
     * Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou
     */
    @Override
    public void execute() {
        WritableImage snapshot = node.snapshot(null, null);
        FileChooser fileChooser = new FileChooser();

        String path = getClass().getResource("/images").getPath();
        //fileChooser.setInitialDirectory(new File(path));
        //fileChooser.setInitialDirectory(new File("files/images"));
        fileChooser.setTitle("Save Image As");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showSaveDialog(stage);
        if (selectedFile != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", selectedFile);
                this.infoBox.addInfo("Image created");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }

}
