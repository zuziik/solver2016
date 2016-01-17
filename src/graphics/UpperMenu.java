package graphics;

import commands.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import sudoku.Sudoku;

/**
 * Trieda reprezentujuca vrchne menu v hlavnom okne
 */
public class UpperMenu extends MenuBar {
    /** Sudoku je hlavna trieda, na ktorej sa budu vykonavat zmeny, preto musi mat menu na nu referenciu, aby ju dalej
     * mohla posunut jednotlivym prikazom */
    Sudoku sudoku;
    /** Menu ma referenciu na hlavne okno, aby ho vedelo poskytnut prikazom, ktore ukoncuju aplikaciu alebo vytvaraju
     * nove sudoku - stare okno sa ma vtedy zavriet */
    Stage root;
    /** Odkaz na textove pole, do ktoreho sa vypisuje pocet rieseni - potrebny ako vystup pre prikaz, ktory pocita
     * riesenia*/
    Label infoBox;

    Menu file = new Menu("File");
    Menu generate = new Menu("Generate");
    Menu settings = new Menu("Settings");
    MenuItem loadSudoku = new MenuItem("Open");
    MenuItem saveSudokuAs = new MenuItem("Save As");
    MenuItem saveSudoku = new MenuItem("Save");
    MenuItem newSudoku = new MenuItem("New");
    MenuItem quit = new MenuItem("Quit");
    MenuItem showSolution = new MenuItem("Show Any Solution");
    MenuItem showProgress = new MenuItem("Show Progress");
    MenuItem countSolutions = new MenuItem("Count Solutions");
    MenuItem clear = new MenuItem("Clear");
    /** Polozka menu na prepinanie medzi modmi s a bez vpisiek - initial je bez nich, ale text sa bude menit podla
     * aktualneho modu*/
    MenuItem switchMode = new MenuItem("Show Pencilmarks");
    MenuItem setTimeout = new MenuItem("Set Timeout");

    public UpperMenu( Stage root, Sudoku sudoku ) {
        this.root = root;
        this.sudoku = sudoku;
        this.getMenus().addAll(file, generate, settings);
        configureFileMenu();
        configureGenerateMenu();
        configureSettingsMenu();
    }

    private void configureFileMenu() {
        file.getItems().addAll(newSudoku, clear, loadSudoku, saveSudoku, saveSudokuAs, quit);

        newSudoku.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Command command = new NewSudokuCommand(root);
                command.execute();
            }
        });
        newSudoku.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));

        loadSudoku.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Command command = new OpenCommand(root);
                command.execute();
            }
        });
        loadSudoku.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));

        saveSudoku.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Command command = new SaveCommand(sudoku);
                command.execute();
            }
        });
        saveSudoku.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

        saveSudokuAs.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Command command = new SaveAsCommand(sudoku, root);
                command.execute();
            }
        });
        saveSudokuAs.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));

        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Command command = new ClearCommand(sudoku);
                command.execute();
            }
        });
        clear.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));

        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Command command = new QuitCommand(root);
                command.execute();
            }
        });
        quit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
    }

    private void configureGenerateMenu() {
        generate.getItems().addAll(countSolutions, showSolution, showProgress);

        countSolutions.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Command command = new CountSolutionsCommand(sudoku, infoBox);
                command.execute();
            }
        });
        countSolutions.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));

        showSolution.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Command command = new ShowSolutionCommand(sudoku);
                command.execute();
            }
        });
        showSolution.setAccelerator(new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN));

        showProgress.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Command command = new ShowProgressCommand(sudoku);
                command.execute();
            }
        });
        showProgress.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN));
    }

    private void configureSettingsMenu() {
        settings.getItems().addAll(setTimeout);

        setTimeout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Command command = new SetTimeoutCommand(sudoku);
                command.execute();
            }
        });
        setTimeout.setAccelerator(new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN));
    }
}
