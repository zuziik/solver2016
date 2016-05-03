package graphics;

import commands.*;
import commands.basic.ClearCommand;
import commands.basic.OutputToInputCommand;
import commands.generate.*;
import commands.io.ExportCommand;
import commands.io.PrintCommand;
import commands.io.ReloadCommand;
import commands.io.SaveCommand;
import graphics.stages.MainStage;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import sudoku.Sudoku;

/**
 * Trieda reprezentuje nastrojovu listu pre hlavne okno
 */
public class ToolBar extends HBox {
    private final Stage root;
    private final MainStage mainStage;
    private final Sudoku sudoku;
    private final InfoBox infoBox;
    private final VBox buttons = new VBox();
    private final Button clear = new Button("Clear Input");
    private final Button save = new Button("Save");
    private final Button reload = new Button("Reload");
    private final Button countSolutions = new Button("Count Solutions");
    private final  Button showSolution = new Button("Show Any Solution");
    private final Button showProgress = new Button("Show Progress");
    private final Button generateSudoku = new Button("Generate Sudoku");
    private final Button printSudoku = new Button("Print Sudoku");
    private final Button createImg = new Button("Create Image");
    private final Button createConsecutive = new Button("Show Consecutive");
    private final Button deleteConsecutive = new Button("Remove Consecutive");
    private final Button transfer = new Button("<< Transfer");

    /**
     * Konstruktor vytvori obsluzny panel v hlavnom okne
     * @param root odkaz na aktualne okno
     * @param mainStage odkaz na hlavne okno
     * @param sudoku odkaz na aktualne sudoku
     * @param infoBox odkaz na tabulu na vypis sprav o priebehu programu
     */
    public ToolBar( Stage root, MainStage mainStage, Sudoku sudoku, InfoBox infoBox ) {
        this.buttons.getChildren().addAll(clear, save, reload, countSolutions, showSolution, showProgress, generateSudoku,
                printSudoku, createImg, createConsecutive, deleteConsecutive, transfer);
        this.getChildren().addAll(buttons, new Rectangle(5,0), infoBox);
        this.sudoku = sudoku;
        this.root = root;
        this.mainStage = mainStage;
        this.infoBox = infoBox;
        setActions();
        setStyles();
        super.setPrefWidth(300);
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
        Style.setButtonStyle(createConsecutive, 150);
        Style.setButtonStyle(deleteConsecutive, 150);
        Style.setButtonStyle(transfer, 150);
    }

    /** Funkcia nastavi spravanie vsetkym tlacidlam */
    private void setActions() {
        clear.setOnAction(event -> {
            Command command = new ClearCommand(sudoku, this.infoBox);
            command.execute();
        });

        save.setOnAction(event1 -> {
            Command command = new SaveCommand(sudoku, this.infoBox);
            command.execute();
        });

        reload.setOnAction(event -> {
            Command command = new ReloadCommand(root, sudoku.getFile(), this.infoBox);
            command.execute();
        });

        countSolutions.setOnAction(event -> {
            Command command = new CountSolutionsCommand(sudoku, infoBox);
            command.execute();
        });

        showProgress.setOnAction(event -> {
            Command command = new ShowProgress(sudoku, infoBox);
            command.execute();
        });

        showSolution.setOnAction(event -> {
            Command command = new ShowSolutionCommand(sudoku, infoBox);
            command.execute();
        });

        generateSudoku.setOnAction(event1 -> {
            Command command = new GenerateSudoku(sudoku, infoBox);
            command.execute();
        });

        printSudoku.setOnAction(event -> {
            Command command = new PrintCommand(root, sudoku, this.infoBox);
            command.execute();
        });

        createImg.setOnAction(event -> {
            Command command = new ExportCommand(sudoku.getInputGrid(), root, this.infoBox);
            command.execute();
        });

        createConsecutive.setOnAction(event -> {
            Command command = new CreateConsecutiveCommand(sudoku, infoBox, mainStage);
            command.execute();
        });

        deleteConsecutive.setOnAction(event -> {
            Command command = new DeleteConsecutiveCommand(sudoku, infoBox, mainStage);
            command.execute();
        });

        transfer.setOnAction(event -> {
            Command command = new OutputToInputCommand(sudoku, this.infoBox);
            command.execute();
        });
    }
}
