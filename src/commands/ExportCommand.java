package commands;

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
 * Created by Zuzka on 7.3.2016.
 */
public class ExportCommand implements Command {
    Node node;
    Stage stage;
    private final InfoBox infoBox;

    public ExportCommand(Node node, Stage stage, InfoBox infoBox) {
        this.node = node;
        this.stage = stage;
        this.infoBox = infoBox;
    }

    @Override
    public void execute() {
        WritableImage snapshot = node.snapshot(null, null);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("files/images"));
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
