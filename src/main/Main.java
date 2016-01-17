package main;

import commands.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
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
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Command command = new NewSudokuCommand(stage);
                command.execute();
            }
        });
        setButtonStyle(button);
        return button;
    }

    private Button createLoadSudokuButton() {
        Button button = new Button("Load Sudoku");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Command command = new OpenCommand(stage);
                command.execute();
            }
        });
        setButtonStyle(button);
        return button;
    }

    private Button createHelpButton() {
        Button button = new Button("Help");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Command command = new HelpCommand();
                command.execute();
            }
        });
        setButtonStyle(button);
        return button;
    }

    private Button createQuitButton() {
        Button button = new Button("Quit");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Command command = new QuitCommand(stage);
                command.execute();
            }
        });
        setButtonStyle(button);
        return button;
    }

    private void setButtonStyle(Button btn) {
        btn.setPrefWidth(width + 20);
        String buttonStyle =
                "-fx-background-color: white;"
                        + "-fx-text-fill: #4A789C;"
                        + "-fx-font-family: 'Arial';"
                        + "-fx-font-weight: bold;";

        String buttonFocusStyle =
                "-fx-background-color: #4A789C;"
                        + "-fx-text-fill: white;"
                        + "-fx-font-family: 'Arial';"
                        + "-fx-font-weight: bold;";

        btn.setStyle(buttonStyle);
        btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                btn.setStyle(null);
                btn.setStyle(buttonFocusStyle);
            }
        });

        btn.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                btn.setStyle(null);
                btn.setStyle(buttonStyle);
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
