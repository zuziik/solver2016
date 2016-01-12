package graphics.stages;

import generators.*;
import graphics.grids.InputGrid;
import graphics.grids.OutputGrid;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Sudoku;

import java.util.List;

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
                inputGrid.getTextFieldLayer().setInputHandlers();   //uz sa to ma spravat ako sudoku, nie grafika
                OutputGrid outputGrid = new OutputGrid(inputGrid);
                Sudoku sudoku = new Sudoku();
                sudoku.setInputGrid(inputGrid);
                sudoku.setOutputGrid(outputGrid);
                Generator generator = createGenerator(sudoku);
                sudoku.setGenerator(generator);
            }
        });
    }

    private Generator createGenerator(Sudoku sudoku) {
        Generator generator = new RowColGenerator(sudoku);

        if (b1_classic.isSelected()) {
            generator = new ClassicGenerator(generator);
        }
        if (b2_diagonal.isSelected()) {
            generator = new DiagonalGenerator(generator);
        }
        if (b3_untouchable.isSelected()) {
            generator = new UntouchableGenerator(generator);
        }
        if (b4_nonconsecutive.isSelected()) {
            generator = new NonconsecutiveGenerator(generator);
        }
        if (b5_disjoint.isSelected()) {
            generator = new DisjointGroupsGenerator(generator);
        }
        if (b6_antiknight.isSelected()) {
            generator = new DisjointGroupsGenerator(generator);
        }

        List<List<Integer>> evens = inputGrid.getEven();
        if (evens.size() > 0) {
            generator = new EvenGenerator(generator, evens);
        }

        List<List<Integer>> odds = inputGrid.getOdd();
        if (odds.size() > 0) {
            generator = new OddGenerator(generator, odds);
        }

        List<List<List<Integer>>> regions = inputGrid.getRegions();
        if (regions.size() > 0) {
            generator = new RegionGenerator(generator, regions);
        }

        return generator;
    }

    public void show() {
        stage.show();
    }
}
