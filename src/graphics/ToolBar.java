package graphics;

import commands.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sudoku.Sudoku;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class ToolBar extends VBox {
    Stage root;
    Sudoku sudoku;
    InfoBox infoBox;
    Button clear = new Button("Clear");
    Button countSolutions = new Button("Count Solutions");
    Button showSolution = new Button("ShowSolution");
    Button showProgress = new Button("ShowProgress");

    public ToolBar( Stage root, Sudoku sudoku, InfoBox infoBox ) {
        this.getChildren().addAll(clear, countSolutions, showSolution, showProgress, infoBox);
        this.sudoku = sudoku;
        this.root = root;
        this.infoBox = infoBox;
        setActions();

    }

    private void setActions() {
        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Command command = new ClearCommand(sudoku);
                command.execute();
            }
        });

        countSolutions.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Command command = new CountSolutionsCommand(sudoku, infoBox);
                command.execute();
            }
        });

        showProgress.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Command command = new ShowProgressCommand(sudoku, infoBox);
                command.execute();
            }
        });

        showSolution.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Command command = new ShowSolutionCommand(sudoku, infoBox);
                command.execute();
            }
        });
    }
}
