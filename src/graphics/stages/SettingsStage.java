package graphics.stages;

import commands.Command;
import commands.CreateCommand;
import graphics.grids.InputGrid;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sudoku.Type;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class SettingsStage {
    BorderPane pane = new BorderPane();
    Scene scene = new Scene(pane);
    Stage stage = new Stage();
    InputGrid inputGrid;
    VBox radioButtons = new VBox();
    RadioButton b1_classic;
    RadioButton b2_diagonal;
    RadioButton b3_untouchable;
    RadioButton b4_nonconsecutive;
    RadioButton b5_disjoint;
    RadioButton b6_antiknight;
    Button create;
    List<List<List<Integer>>> irregulars;
    List<List<List<Integer>>> extras;
    List<List<Integer>> odds;
    List<List<Integer>> evens;
    Set<Type> types;

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
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    create.fire();
                }
            }
        });
    }

    private void createRadioButtons() {
        b1_classic = new RadioButton("Classic");
        b1_classic.setSelected(true);
        b2_diagonal = new RadioButton("Diagonal");
        b3_untouchable = new RadioButton("Untouchable");
        b4_nonconsecutive = new RadioButton("Nonconsecutive");
        b5_disjoint = new RadioButton("Disjoint Groups");
        b6_antiknight = new RadioButton("Antiknight");

        setUpdateActions(b1_classic);
        setUpdateActions(b2_diagonal);
        setUpdateActions(b3_untouchable);
        setUpdateActions(b4_nonconsecutive);
        setUpdateActions(b5_disjoint);
        setUpdateActions(b6_antiknight);

        b1_classic.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (b1_classic.isSelected()) {
                    inputGrid.getBorderLayer().showBorders();
                } else {
                    inputGrid.getBorderLayer().hideBorders();
                }
            }
        });

        b2_diagonal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (b2_diagonal.isSelected()) {
                    inputGrid.getDiagonalLayer().showDiagonals();
                } else {
                    inputGrid.getDiagonalLayer().hideDiagonals();
                }
            }
        });

        radioButtons.getChildren().addAll(b1_classic, b2_diagonal, b3_untouchable, b4_nonconsecutive, b5_disjoint, b6_antiknight);
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
        create.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                inputGrid.getTextFieldLayer().changeGrid();
                updateLists();
                Command command = new CreateCommand(null, stage, types, irregulars, extras, evens, odds);
                command.execute();
            }
        });
    }

    private void updateLists() {
        this.types = new HashSet<>();

        if (b1_classic.isSelected()) {
            types.add(Type.Classic);
        }
        if (b2_diagonal.isSelected()) {
            types.add(Type.Diagonal);
        }
        if (b3_untouchable.isSelected()) {
            types.add(Type.Untouchable);
        }
        if (b4_nonconsecutive.isSelected()) {
            types.add(Type.NonConsecutive);
        }
        if (b5_disjoint.isSelected()) {
            types.add(Type.DisjointGroups);
        }
        if (b6_antiknight.isSelected()) {
            types.add(Type.Antiknight);
        }

        List<List<Integer>> evens = inputGrid.getEven();
        if (evens.size() > 0) {
            types.add(Type.Even);
            this.evens = evens;
        }

        List<List<Integer>> odds = inputGrid.getOdd();
        if (odds.size() > 0) {
            types.add(Type.Odd);
            this.odds = odds;
        }

        List<List<List<Integer>>> irregulars = inputGrid.getIrregulars();
        if (irregulars.size() > 0) {
            types.add(Type.Irregular);
            this.irregulars = irregulars;
        }

        List<List<List<Integer>>> extras = inputGrid.getExtras();
        if (extras.size() > 0) {
            types.add(Type.ExtraRegion);
            this.extras = extras;
        }

    }

    public void show() {
        stage.show();
    }
}
