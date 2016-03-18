package graphics;

import commands.*;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sudoku.Sudoku;

/**
 * Trieda reprezentuje nastrojovu listu pre hlavne okno
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
    private final Button generateSudoku = new Button("Generate Sudoku");
    private final Button printSudoku = new Button("Print Sudoku");
    private final Button createImg = new Button("Create Image");

    public ToolBar( Stage root, Sudoku sudoku, InfoBox infoBox ) {
        this.getChildren().addAll(clear, save, reload, countSolutions, showSolution, showProgress, generateSudoku, printSudoku, createImg, infoBox);
        this.sudoku = sudoku;
        this.root = root;
        this.infoBox = infoBox;
        setActions();
        setStyles();
        super.setPrefWidth(150);

    }

    /** Funkcia nastavi styl vsetkym tlacidlam */
    private void setStyles() {
        Style.setButtonStyle(clear, 150);
        Style.setButtonStyle(save, 150);
        Style.setButtonStyle(reload, 150);
        Style.setButtonStyle(countSolutions, 150);
        Style.setButtonStyle(showSolution, 150);
        Style.setButtonStyle(showProgress, 150);
        Style.setButtonStyle(generateSudoku, 150);
        Style.setButtonStyle(printSudoku, 150);
        Style.setButtonStyle(createImg, 150);
    }

    /** Funkcia nastavi spravanie vsetkym tlacidlam */
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
            Command command = new ShowProgressFast(sudoku, infoBox);
            command.execute();
        });

        showSolution.setOnAction(event -> {
            Command command = new ShowSolutionCommand(sudoku, infoBox);
            command.execute();
        });

        generateSudoku.setOnAction(event1 -> {
            Command command = new GenerateCommand(sudoku, infoBox);
            command.execute();
        });

        printSudoku.setOnAction(event -> {
            Command command = new PrintCommand(root, sudoku);
            command.execute();
        });

        createImg.setOnAction(event -> {
            Command command = new ExportCommand(sudoku.getInputGrid(), root);
            command.execute();
        });
    }
}
