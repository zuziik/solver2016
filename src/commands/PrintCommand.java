package commands;

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
 * Created by Zuzka on 7.3.2016.
 */
public class PrintCommand implements Command {

    private Stage stage;
    private Sudoku sudoku;

    public PrintCommand(Stage stage, Sudoku sudoku) {
        this.stage = stage;
        this.sudoku = sudoku;
    }

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
        }
    }
}
