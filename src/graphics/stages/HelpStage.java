package graphics.stages;

import commands.Command;
import commands.basic.QuitCommand;
import graphics.Style;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Trieda reprezentuje okno s informaciami o aplikacii
 */
public class HelpStage {
    private final BorderPane pane = new BorderPane();
    private final Scene scene = new Scene(pane);
    private final Stage stage = new Stage();
    private Label help = new Label("");
    private final Button ok;
    private final Button lang;

    /** Funkcia precita informacie o aplikacii zo suboru prislusneho jazyka */
    private String readHelp() {
        String file = "files/help"+lang.getText()+".txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(file)));
            String content = "";
            String row = br.readLine();
            while (row != null) {
                content += row + "\n";
                row = br.readLine();
            }
            br.close();
            return content;
        }
        catch (FileNotFoundException e) {
            System.err.println("File with help does not exists :(");
        }
        catch (Exception e) {
            System.err.println("Error appeared while reading file with help");
            e.printStackTrace();
        }
        return null;
    }

    public HelpStage() {
        this.stage.setScene(scene);
        this.stage.setTitle("Help");
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setAlwaysOnTop(true);
        this.help.setPrefWidth(600);
        this.help.setPrefHeight(650);
        this.help.setWrapText(true);
        this.ok = new Button("GOT IT!");
        this.ok.setOnAction( event -> {
            Command command = new QuitCommand(stage);
            command.execute();
        });
        this.lang = new Button("EN");
        this.help.setText(readHelp());
        this.lang.setText("SK");
        this.lang.setOnAction(event -> {
            if (lang.getText().equals("SK")) {
                help.setText(readHelp());
                lang.setText("EN");
            }
            else {
                help.setText(readHelp());
                lang.setText("SK");
            }
        });
        Style.setButtonStyle(lang,600);
        Style.setButtonStyle(ok,600);
        pane.setTop(lang);
        pane.setCenter(help);
        pane.setBottom(ok);
    }

    /** Funkcia zabezpeci zobrazenie okna s informaciami o aplikacii */
    public void show() {
        this.stage.show();
    }
}
