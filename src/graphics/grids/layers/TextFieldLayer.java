package graphics.grids.layers;

import graphics.grids.InputGrid;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

import java.util.Set;
import java.util.TreeSet;


/**
 * Created by Zuzka on 10.1.2016.
 */
public class TextFieldLayer extends GridPane {

    private final int size;
    private final InputGrid inputGrid;
    private final TextField[][] textFields = new TextField[9][9];

    public TextFieldLayer(int size, InputGrid inputGrid) {
        this.inputGrid = inputGrid;
        this.size = size;
        super.setHgap(1);
        super.setVgap(1);

        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {
                TextField t = new TextField();
                t.setPrefWidth(size);
                t.setPrefHeight(size);
                t.setAlignment(Pos.CENTER);
                t.setBackground(Background.EMPTY);
                textFields[i][j] = t;
                super.add(t, j, i);
            }
        }

        this.setInputHandlers();
    }

    public void setSettingsHandlers() {
        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {
                setSettingsHandler(i, j);
                textFields[i][j].setFont(new Font(10));
            }
        }
    }

    private void setSettingsHandler(int x, int y) {

        textFields[x][y].setOnKeyPressed(event -> {
            TextField up = textFields[(x + 8) % 9][y];
            TextField down = textFields[(x + 1) % 9][y];
            TextField left = textFields[x][(y + 8) % 9];
            TextField right = textFields[x][(y + 1) % 9];

            if (event.getCode().equals(KeyCode.DOWN)) {
                down.requestFocus();
                changeGrid(x, y);
            } else if (event.getCode().equals(KeyCode.UP)) {
                up.requestFocus();
                changeGrid(x, y);
            } else if (event.getCode().equals(KeyCode.LEFT)) {
                left.requestFocus();
                changeGrid(x, y);
            } else if (event.getCode().equals(KeyCode.RIGHT)) {
                right.requestFocus();
                changeGrid(x, y);
            }
        });

        textFields[x][y].setOnMouseClicked(event -> changeGrid());
    }

    /** Funkcia sa pouziva pri nastavovani variant pre sudoku. Zabezpeci adekvatnu zmenu policok, napriklad zmenu farby,
     * pridanie Diagonal, kruzkov... */
    public void changeGrid() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                changeGrid(i, j);
            }
        }
    }

    private void changeGrid(int x, int y) {
        TextField textField = textFields[x][y];

        String irregular = changeIrregular(x, y);
        String extraRegion = changeRegions(x, y);
        String parity = changeParity(x, y);

        textField.setText(irregular + extraRegion + parity);
    }

    public void setInputHandlers() {
        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {
                textFields[i][j].setFont(new Font("Arial",20));
                setInputHandler(i, j);
            }
        }
    }

    private void setInputHandler(int x, int y) {
        textFields[x][y].setOnKeyPressed(event -> {
            TextField up = textFields[(x+8)%9][y];
            TextField down = textFields[(x+1)%9][y];
            TextField left = textFields[x][(y+8)%9];
            TextField right = textFields[x][(y+1)%9];

            if (event.getCode().equals(KeyCode.DOWN)) {
                down.requestFocus();
                filterText(x, y);
            } else if (event.getCode().equals(KeyCode.UP)) {
                up.requestFocus();
                filterText(x, y);
            } else if (event.getCode().equals(KeyCode.LEFT)) {
                left.requestFocus();
                filterText(x, y);
            } else if (event.getCode().equals(KeyCode.RIGHT)) {
                right.requestFocus();
                filterText(x, y);
            }
        });

        textFields[x][y].setOnMouseClicked(event -> updateGrid());
    }

    public void updateGrid() {
        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {
                filterText(i,j);
            }
        }
    }

    private String changeIrregular(int x, int y) {
        TextField textField = textFields[x][y];
        String text = textField.getText();

        String irregular = "";
        for ( Integer i = 1; i <= 9; i++ ) {
            if (text.contains(i.toString())) {
                inputGrid.getIrregularLayer().color(x, y, i);
                irregular = i.toString();
            }
        }

        if (irregular.equals("")) {
            inputGrid.getIrregularLayer().setBlank(x, y);
        }

        return irregular;
    }

    private String changeRegions(int x, int y) {
        TextField textField = textFields[x][y];
        String text = textField.getText();
        String extraRegions = "";

        for (Character c = 'A'; c <= 'D'; c++ ) {
            if (text.contains(c.toString()) || text.contains(Character.toString(Character.toLowerCase(c)))) {
                inputGrid.getRegionLayer().color(x, y, c);
                extraRegions = c.toString();
            }
        }

        if (extraRegions.equals("")) {
            inputGrid.getRegionLayer().setBlank(x, y);
        }

        return extraRegions;
    }

    private String changeParity(int x, int y) {
        TextField textField = textFields[x][y];
        String text = textField.getText();
        String parity = "";

        if (text.contains("O") || text.contains("o")) {
            inputGrid.getParityLayer().color(x, y, 'O');
            parity = "O";
        }
        if (text.contains("E") || text.contains("e")) {
            inputGrid.getParityLayer().color(x, y, 'E');
            parity = "E";
        }
        if (parity.equals("")) {
            inputGrid.getParityLayer().setBlank(x, y);
        }

        return parity;
    }



    private void filterText(int x, int y) {
        TextField text = textFields[x][y];
        String s = "";
        for (Integer i = 1; i <= 9; i++) {
            if (text.getText().contains(i.toString())) {
                s = i.toString();
            }
        }
        text.setText(s);
    }

    public Set<Integer> getOptions(int x, int y) {
        Set<Integer> options = new TreeSet<>();
        String text = textFields[x][y].getText();
        if (text.equals("")) {
            for ( Integer i = 0; i < 9; i++ ) {
                options.add(i);
            }
        }
        else {
            for ( Integer i = 1; i <= 9; i++ ) {
                if (text.contains(i.toString())) {
                    options.add(i-1);
                }
            }
        }
        return options;
    }

    public String getText(int x, int y) {
        return textFields[x][y].getText();
    }

    public void setText(int x, int y, String text) {
        this.textFields[x][y].setText(text);
    }

    public TextField[][] getTextFields() {
        return this.textFields;
    }

}
