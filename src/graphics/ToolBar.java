package graphics;

import commands.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sudoku.Sudoku;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class ToolBar extends VBox {
    private final Stage root;
    private final Sudoku sudoku;
    private final InfoBox infoBox;
    private final Button clear = new Button("Clear Input");
    private final Button save = new Button("Save");
    private final Button reload = new Button("Reload");
    private final Button countSolutions = new Button("Count Solutions");
    private final  Button showSolution = new Button("Show Any Solution");
    private final Button showProgress = new Button("Show Progress");

    public ToolBar( Stage root, Sudoku sudoku, InfoBox infoBox ) {
        this.getChildren().addAll(clear, save, reload, countSolutions, showSolution, showProgress, infoBox);
        this.sudoku = sudoku;
        this.root = root;
        this.infoBox = infoBox;
        setActions();
        setStyles();
        super.setPrefWidth(150);

    }

    private void setStyles() {
        Style.setButtonStyle(clear, 150);
        Style.setButtonStyle(save, 150);
        Style.setButtonStyle(reload, 150);
        Style.setButtonStyle(countSolutions, 150);
        Style.setButtonStyle(showSolution, 150);
        Style.setButtonStyle(showProgress, 150);
    }

    private void setActions() {
        clear.setOnAction(event -> {
            Command command = new ClearCommand(sudoku);
            command.execute();
        });

        save.setOnAction(event1 -> {
            Command command = new SaveCommand(sudoku);
            command.execute();
        });

        reload.setOnAction(event -> {
            Command command = new ReloadCommand(root, sudoku.getFile());
            command.execute();
        });

        countSolutions.setOnAction(event -> {
            Command command = new CountSolutionsCommand(sudoku, infoBox);
            command.execute();
        });

        showProgress.setOnAction(event -> {
            Command command = new ShowProgressCommand(sudoku, infoBox);
            command.execute();
        });

        showSolution.setOnAction(event -> {
            Command command = new ShowSolutionCommand(sudoku, infoBox);
            command.execute();
        });
    }
}
