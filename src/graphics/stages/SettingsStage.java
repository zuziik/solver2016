package graphics.stages;

import commands.Command;
import commands.QuitCommand;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sudoku.Sudoku;

/**
 * Created by Zuzka on 21.1.2016.
 */
public class SettingsStage {
    BorderPane pane = new BorderPane();
    Scene scene = new Scene(pane);
    Stage stage = new Stage();
    Slider slider;
    Sudoku sudoku;

    public SettingsStage(Sudoku sudoku) {
        this.stage.setScene(scene);
        this.stage.setTitle("Settings");
        this.sudoku = sudoku;

        this.slider = new Slider(1, 10, 5);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(0.25f);
        slider.setBlockIncrement(0.1f);

        Button ok = new Button("Set Timeout");
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sudoku.getGenerator().setTimeLimit(slider.getValue());
                Command command = new QuitCommand(stage);
                command.execute();
            }
        });

        pane.setTop(new Label("Set Timeout for the generator (in secs):"));
        pane.setCenter(slider);
        pane.setBottom(ok);
    }

    public void show() {
        this.stage.show();
    }
}
