package graphics;

import commands.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import main.Sudoku;

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
    MenuItem loadSudoku = new MenuItem("Load Sudoku");
    MenuItem saveSudoku = new MenuItem("Save Sudoku");
    MenuItem newSudoku = new MenuItem("New Sudoku");
    MenuItem quit = new MenuItem("Quit");
    MenuItem showSolution = new MenuItem("Show Solution");
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
        file.getItems().addAll(newSudoku, loadSudoku, saveSudoku, quit);

        newSudoku.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Command command = new NewSudokuCommand(root);
                command.execute();
            }
        });

        loadSudoku.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Command command = new LoadCommand(root);
                command.execute();
            }
        });

        saveSudoku.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Command command = new SaveCommand(sudoku);
                command.execute();
            }
        });

        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Command command = new QuitCommand(root);
                command.execute();
            }
        });
    }

    private void configureGenerateMenu() {
        generate.getItems().addAll(countSolutions,showSolution,showProgress, clear);

        countSolutions.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Command command = new CountSolutionsCommand(sudoku, infoBox);
                command.execute();
            }
        });

        showSolution.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Command command = new ShowSolutionCommand(sudoku);
                command.execute();
            }
        });

        showProgress.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Command command = new ShowProgressCommand(sudoku);
                command.execute();
            }
        });

        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Command command = new ClearCommand(sudoku);
                command.execute();
            }
        });
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
    }
}
