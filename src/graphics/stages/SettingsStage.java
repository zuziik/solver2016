package graphics.stages;

import commands.Command;
import commands.QuitCommand;
import graphics.Style;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sudoku.Sudoku;

/**
 * Trieda reprezentuje okno s dalsimi nastaveniami
 */
public class SettingsStage {
    private final BorderPane pane = new BorderPane();
    private final Scene scene = new Scene(pane, 400, 100);
    private final Stage stage = new Stage();
    private final Slider slider;
    private final Sudoku sudoku;
    private double def;

    public SettingsStage(Sudoku sudoku) {
        this.stage.setScene(scene);
        this.stage.setTitle("Settings");
        this.stage.setResizable(false);
        this.stage.setAlwaysOnTop(true);
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.sudoku = sudoku;
        this.def = this.sudoku.getGenerator().getTimeLimit();
        this.slider = new Slider(1, 10, def);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(0.25f);
        slider.setBlockIncrement(0.1f);
        slider.setPrefWidth(200);

        Button ok = new Button("Set Timeout");
        ok.setOnAction( event -> {
            sudoku.getGenerator().setTimeLimit(slider.getValue());
            Command command = new QuitCommand(stage);
            command.execute();
        });
        Style.setButtonStyle(ok, 420);

        pane.setTop(new Label("Set Timeout for the generator (in secs):"));
        pane.setCenter(slider);
        pane.setBottom(ok);
    }

    /** Funkcia zabezpeci zobrazenie okna s dalsimi nastaveniami */
    public void show() {
        this.stage.show();
    }
}
