package graphics;

import commands.*;
import graphics.stages.MainStage;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import sudoku.Sudoku;

/**
 * Trieda reprezentuje vrchne menu v hlavnom okne
 */
public class UpperMenu extends MenuBar {
    /** Sudoku je hlavna trieda, na ktorej sa budu vykonavat zmeny, preto musi mat menu na nu referenciu, aby ju dalej
     * mohla posunut jednotlivym prikazom */
    private final Sudoku sudoku;
    /** Menu ma referenciu na hlavne okno, aby ho vedelo poskytnut prikazom, ktore ukoncuju aplikaciu alebo vytvaraju
     * nove sudoku - stare okno sa ma vtedy zavriet */
    private final Stage root;
    private final MainStage mainStage;
    /** Odkaz na textove pole, do ktoreho sa vypisuje pocet rieseni - potrebny ako vystup pre prikaz, ktory pocita
     * riesenia*/
    private final InfoBox infoBox;

    private final Menu file = new Menu("File");
    private final Menu generate = new Menu("Generate");
    private final Menu content = new Menu("Content");
    private final Menu settings = new Menu("Settings");
    private final Menu export = new Menu("Export");
    private final MenuItem loadSudoku = new MenuItem("Open");
    private final MenuItem reloadSudoku = new MenuItem("Reload");
    private final MenuItem saveSudokuAs = new MenuItem("Save As");
    private final MenuItem saveSudoku = new MenuItem("Save");
    private final MenuItem newSudoku = new MenuItem("New");
    private final MenuItem help = new MenuItem("Help");
    private final MenuItem quit = new MenuItem("Quit");
    private final MenuItem showSolution = new MenuItem("Show Any Solution");
    private final MenuItem showProgress = new MenuItem("Show Progress");
    private final MenuItem countSolutions = new MenuItem("Count Solutions");
    private final MenuItem generateSudoku = new MenuItem("Generate Sudoku");
    private final MenuItem createConsecutive = new MenuItem("Create Consecutive");
    private final MenuItem deleteConsecutive = new MenuItem("Delete Consecutive");
    private final MenuItem transfer = new MenuItem("<< Transfer");
    private final MenuItem clear = new MenuItem("Clear Input");
    private final MenuItem print = new MenuItem("Print Sudoku");
    private final MenuItem saveImg = new MenuItem("Save as Image");
    private final MenuItem setTimeout = new MenuItem("Set Timeout");

    public UpperMenu( Stage root, MainStage mainStage, Sudoku sudoku, InfoBox infoBox ) {
        this.root = root;
        this.mainStage = mainStage;
        this.sudoku = sudoku;
        this.infoBox = infoBox;
        this.getMenus().addAll(file, generate, content, settings, export);
        configureFileMenu();
        configureGenerateMenu();
        configureContentMenu();
        configureSettingsMenu();
        configureExportMenu();
    }

    /** Funkcia nastavi spravanie polozkam menu File */
    private void configureFileMenu() {
        file.getItems().addAll(newSudoku, clear, reloadSudoku, loadSudoku, saveSudoku, saveSudokuAs, help, quit);

        newSudoku.setOnAction(event -> {
            Command command = new NewSudokuCommand(root);
            command.execute();
        });
        newSudoku.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));

        reloadSudoku.setOnAction(event1 -> {
            Command command = new ReloadCommand(root, sudoku.getFile(), this.infoBox);
            command.execute();
        });
        reloadSudoku.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN));

        loadSudoku.setOnAction(event -> {
            Command command = new OpenCommand(root, this.infoBox);
            command.execute();
        });
        loadSudoku.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));

        saveSudoku.setOnAction(event -> {
            Command command = new SaveCommand(sudoku, this.infoBox);
            command.execute();
        });
        saveSudoku.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

        saveSudokuAs.setOnAction(event -> {
            Command command = new SaveAsCommand(sudoku, root, this.infoBox);
            command.execute();
        });
        saveSudokuAs.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));

        clear.setOnAction(event -> {
            Command command = new ClearCommand(sudoku, this.infoBox);
            command.execute();
        });
        clear.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));

        help.setOnAction(event -> {
            Command command = new HelpCommand();
            command.execute();
        });
        help.setAccelerator(new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN));

        quit.setOnAction(event -> {
            Command command = new QuitCommand(root);
            command.execute();
        });
        quit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
    }

    /** Funkcia nastavi spravanie polozkam menu Generate */
    private void configureGenerateMenu() {
        generate.getItems().addAll(countSolutions, showSolution, showProgress, generateSudoku);

        countSolutions.setOnAction(event -> {
            Command command = new CountSolutionsCommand(sudoku, infoBox);
            command.execute();
        });
        countSolutions.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));

        showSolution.setOnAction(event -> {
            Command command = new ShowSolutionCommand(sudoku, infoBox);
            command.execute();
        });
        showSolution.setAccelerator(new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN));

        showProgress.setOnAction(event -> {
            Command command = new ShowProgressFast(sudoku, infoBox);
            command.execute();
        });
        showProgress.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN));

        generateSudoku.setOnAction(event -> {
            Command command = new GenerateSudoku(sudoku, infoBox);
            command.execute();
        });
        generateSudoku.setAccelerator(new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN));
    }

    private void configureContentMenu() {
        content.getItems().addAll(createConsecutive, deleteConsecutive, transfer);

        createConsecutive.setOnAction(event -> {
            Command command = new CreateConsecutiveCommand(sudoku, infoBox, mainStage);
            command.execute();
        });
        createConsecutive.setAccelerator(new KeyCodeCombination(KeyCode.K, KeyCombination.CONTROL_DOWN));

        deleteConsecutive.setOnAction(event -> {
            Command command = new DeleteConsecutiveCommand(sudoku, infoBox, mainStage);
            command.execute();
        });
        deleteConsecutive.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN));

        transfer.setOnAction(event -> {
            Command command = new OutputToInputCommand(sudoku, this.infoBox);
            command.execute();
        });
        transfer.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN));
    }

    /** Funkcia nastavi spravanie polozkam menu Settings */
    private void configureSettingsMenu() {
        settings.getItems().addAll(setTimeout);

        setTimeout.setOnAction(event -> {
            Command command = new SetTimeoutCommand(sudoku);
            command.execute();
        });
        setTimeout.setAccelerator(new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN));
    }

    /** Funkcia nastavi spravanie polozkam menu Export */
    private void configureExportMenu() {
        export.getItems().addAll(print, saveImg);

        print.setOnAction(event -> {
            Command command = new PrintCommand(root, sudoku, this.infoBox);
            command.execute();
        });
        print.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN));

        saveImg.setOnAction(event -> {
            Command command = new ExportCommand(sudoku.getInputGrid(), root, this.infoBox);
            command.execute();
        });
        saveImg.setAccelerator(new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN));
    }
}
