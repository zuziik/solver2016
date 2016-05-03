package commands.io;

import commands.Command;
import graphics.InfoBox;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sudoku.Rules;
import sudoku.Sudoku;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

/**
 * Trieda reprezentuje prikaz, ktory spusti dialogove okno na vytlacenie aktualnej mriezky sudoku.
 */
public class PrintCommand implements Command {

    private Stage stage;
    private Sudoku sudoku;
    private InfoBox infoBox;

    /**
     * @param sudoku aktualne sudoku
     * @param infoBox tabula, na ktoru sa vypisuju hlasky o cinnosti
     */
    public PrintCommand(Stage stage, Sudoku sudoku, InfoBox infoBox) {
        this.stage = stage;
        this.sudoku = sudoku;
        this.infoBox = infoBox;
    }

    /**
     * Funkcia vytvori jeden graficky objekt so vsetkymi informaciami urceny na tlac.
     */
    private Node createNode() {
        VBox vBox = new VBox();
        Label timeStamp = new Label(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        Label filePath = new Label(this.sudoku.getFile().getPath());
        Label types = new Label(this.sudoku.getTypesText());
        Node node = sudoku.getInputGrid().printImage();
        vBox.getChildren().addAll(types, timeStamp, filePath, node);
        return vBox;
    }

    /**
     * Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou
     */
    @Override
    public void execute() {
        PrinterJob job = PrinterJob.createPrinterJob();
        Node node = this.createNode();
        if (job.showPrintDialog(stage.getOwner()) && job.printPage(node)) {
            job.endJob();
            this.infoBox.addInfo("Sudoku printed");
        }
    }
}
