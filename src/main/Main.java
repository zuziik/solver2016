package main;

import commands.*;
import commands.basic.HelpCommand;
import commands.basic.NewSudokuCommand;
import commands.basic.QuitCommand;
import commands.io.OpenCommand;
import graphics.Style;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    BorderPane pane = new BorderPane();
    Stage stage;
    int width = 300;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);

        primaryStage.setTitle("SUDOKU Solution Checker");
        primaryStage.setResizable(false);

        setPaneProperties();

        VBox buttons = createButtons();
        pane.setBottom(buttons);

        primaryStage.show();

    }

    private void setPaneProperties() {
        pane.setPrefHeight(width);
        pane.setPrefWidth(width);

        Image image = new Image(getClass().getResourceAsStream("../images/sudoku.png"));
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        pane.setBackground(background);
    }

    private VBox createButtons(){
        VBox buttons = new VBox();
        Button newSudoku = createNewSudokuButton();
        Button loadSudoku = createLoadSudokuButton();
        Button help = createHelpButton();
        Button quit = createQuitButton();
        buttons.getChildren().addAll(newSudoku, loadSudoku, help, quit);
        return buttons;
    }

    private Button createNewSudokuButton() {
        Button button = new Button("New Sudoku");
        button.setOnAction(event -> {
            Command command = new NewSudokuCommand(stage);
            command.execute();
        });
        setButtonStyle(button);
        return button;
    }

    private Button createLoadSudokuButton() {
        Button button = new Button("Load Sudoku");
        button.setOnAction(event -> {
            Command command = new OpenCommand(stage, null);
            command.execute();
        });
        setButtonStyle(button);
        return button;
    }

    private Button createHelpButton() {
        Button button = new Button("Help");
        button.setOnAction(event -> {
            Command command = new HelpCommand();
            command.execute();
        });
        setButtonStyle(button);
        return button;
    }

    private Button createQuitButton() {
        Button button = new Button("Quit");
        button.setOnAction(event -> {
            Command command = new QuitCommand(stage);
            command.execute();
        });
        setButtonStyle(button);
        return button;
    }

    private void setButtonStyle(Button btn) {
        btn.setPrefWidth(width + 20);
        Style.setHelloButtonStyle(btn);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
