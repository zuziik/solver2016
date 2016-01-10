package graphics.stages;

import graphics.grids.InputGrid;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class SettingsStage {
    BorderPane pane = new BorderPane();
    Scene scene = new Scene(pane);
    Stage stage = new Stage();
    InputGrid inputGrid;
    VBox radioButtons = new VBox();
    RadioButton b1;
    RadioButton b2;
    RadioButton b3;
    RadioButton b4;
    RadioButton b5;
    RadioButton b6;
    Button create;

    public SettingsStage() {
        stage.setScene(scene);
        stage.setTitle("Settings");
        inputGrid = new InputGrid();
        pane.setLeft(inputGrid);
        createRadioButtons();
        create = new Button("Create");
        addCreateHandlers();
        radioButtons.getChildren().add(create);
        pane.setRight(radioButtons);
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                inputGrid.getTextFieldLayer().changeGrid();
            }
        });
    }

    private void createRadioButtons() {
        b1 = new RadioButton("Classic");
        b1.setSelected(true);
        b2 = new RadioButton("Diagonal");
        b3 = new RadioButton("Untouchable");
        b4 = new RadioButton("Nonconsecutive");
        b5 = new RadioButton("Disjoint Groups");
        b6 = new RadioButton("Antiknight");

        setUpdateActions(b1);
        setUpdateActions(b2);
        setUpdateActions(b3);
        setUpdateActions(b4);
        setUpdateActions(b5);
        setUpdateActions(b6);

        b1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (b1.isSelected()) {
                    inputGrid.getBorderLayer().showBorders();
                } else {
                    inputGrid.getBorderLayer().hideBorders();
                }
            }
        });

        b2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (b2.isSelected()) {
                    inputGrid.getDiagonalLayer().showDiagonals();
                } else {
                    inputGrid.getDiagonalLayer().hideDiagonals();
                }
            }
        });

        radioButtons.getChildren().addAll(b1, b2, b3, b4, b5, b6);
    }

    private void setUpdateActions(RadioButton b) {
        b.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                inputGrid.getTextFieldLayer().changeGrid();
            }
        });
    }

    private void addCreateHandlers() {
        //TODO
    }

    public void show() {
        stage.show();
    }
}
