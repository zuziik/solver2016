package graphics.stages;

import commands.Command;
import commands.basic.QuitCommand;
import graphics.Style;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sudoku.Sudoku;

/**
 * Trieda reprezentuje okno s dalsimi nastaveniami
 */
public class SettingsStage {
    private final BorderPane pane = new BorderPane();
    private final Scene scene = new Scene(pane, 400, 180);
    private final Stage stage = new Stage();
    private final Slider slider1;
    private final Slider slider2;
    private final Label infoTimeout;
    private final Label infoRepetitions;
    private final Label infoGeneratingTime;
    private final VBox vBox;
    private final Sudoku sudoku;
    private double defaultLimit;
    private int defaultRepetitions;

    public SettingsStage(Sudoku sudoku) {
        this.stage.setScene(scene);
        this.stage.setTitle("Settings");
        this.stage.setResizable(false);
        this.stage.setAlwaysOnTop(true);
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.sudoku = sudoku;

        this.defaultLimit = this.sudoku.getGenerator().getTimeLimit();
        this.slider1 = new Slider(1, 10, defaultLimit);
        this.slider1.setShowTickMarks(true);
        this.slider1.setShowTickLabels(true);
        this.slider1.setMajorTickUnit(0.25f);
        this.slider1.setBlockIncrement(0.1f);
        this.slider1.setPrefWidth(400);

        this.defaultRepetitions = this.sudoku.getGenerator().getRepetitionsLimit();
        this.slider2 = new Slider(0, 200, this.defaultRepetitions);
        this.slider2.setShowTickMarks(true);
        this.slider2.setShowTickLabels(true);
        this.slider2.setMajorTickUnit(10);
        this.slider2.setBlockIncrement(10);
        this.slider2.setPrefWidth(400);

        this.vBox = new VBox();
        this.infoTimeout = new Label("Timeout: "+this.defaultLimit+" sec");
        this.infoTimeout.setPrefWidth(400);
        this.infoRepetitions = new Label("Repetitions: "+this.defaultRepetitions+" solutions");
        this.infoRepetitions.setPrefWidth(400);
        this.infoGeneratingTime = new Label("Generating time: "+Math.round(this.defaultLimit*this.defaultRepetitions)+" sec");
        this.infoGeneratingTime.setPrefWidth(400);

        this.vBox.getChildren().addAll(this.infoTimeout, this.slider1, this.infoRepetitions, this.slider2, this.infoGeneratingTime);

        Button ok = new Button("Set Timeout");
        ok.setOnAction(event -> {
            sudoku.getGenerator().setTimeLimit(slider1.getValue());
            sudoku.getGenerator().setRepetitionsLimit((int) slider2.getValue());
            Command command = new QuitCommand(stage);
            command.execute();
        });
        Style.setButtonStyle(ok, 420);

        slider1.setOnMouseClicked(event -> this.updateInfo());

        slider1.setOnMouseReleased(event -> this.updateInfo());

        slider1.setOnKeyReleased(event -> this.updateInfo());

        slider2.setOnMouseClicked(event -> this.updateInfo());

        slider2.setOnMouseReleased(event -> this.updateInfo());

        slider2.setOnKeyReleased(event -> this.updateInfo());

        pane.setTop(new Label("Set Timeout for the generator (in secs):"));
        pane.setCenter(this.vBox);
        pane.setBottom(ok);

        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                ok.fire();
            }
        });
    }

    private void updateInfo() {
        double limit = ((double) Math.round(this.slider1.getValue()*10))/10;
        double time = ((double) Math.round(this.slider1.getValue()*this.slider2.getValue()*10))/10;
        int minutes = (int) time/60;
        double seconds = ((double) Math.round((time - 60*minutes)*10))/10;
        this.infoTimeout.setText("Timeout: "+limit+" sec");
        this.infoRepetitions.setText("Repetitions: "+Math.round(this.slider2.getValue())+" solutions");
        this.infoGeneratingTime.setText("Generating time: "+time+" sec = "+minutes+" min "+seconds+" sec");
    }

    /** Funkcia zabezpeci zobrazenie okna s dalsimi nastaveniami */
    public void show() {
        this.stage.show();
    }
}
