package graphics.grids.layers;

import graphics.InfoBox;
import graphics.grids.InputGrid;
import javafx.geometry.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Funkcia reprezentuje vrstvu s textovymi polami na zadavanie obsahu sudoku pouzivatelom
 */
public class TextFieldLayer extends GridPane {

    private final int size;
    private final InputGrid inputGrid;
    private final TextField[][] textFields = new TextField[9][9];
    private Integer givens;
    private InfoBox infoBox;

    /**
     * Konstruktor vytvori vrstvu na vykreslovanie cisel v sudoku - vo vstupnej mriezke.
     * @param size velkost jedneho policka v sudoku
     */
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

    /** Funkcia nastavi vrstve referenciu na infoBox
     * @param infoBox referencia na tabulu na vypis sprav o priebehu programu
     */
    public void setInfoBox(InfoBox infoBox) {
        this.infoBox = infoBox;
    }

    /** Funkcia textovym poliam nastavi spravanie, ak je vrstva pouzita pri nastaveniach mriezky */
    public void setSettingsHandlers() {
        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {
                setSettingsHandler(i, j);
                textFields[i][j].setFont(new Font(10));
            }
        }
    }

    /** Funkcia nastavi textovemu polu na pozicii x, y spravanie pre konfigurovanie mriezky a nastavenie typov
     * @param x x-ova suradnica policka
     * @param y y-ova suradnica policka
     */
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

    /** Funkcia aktualizuje mriezku podla zadanych hodnot - prefarbi policka podla ich prislusnosti k regionom a oznaci
     * ich kruzkami, ak boli oznacene ako parne ci neparne. Okrem toho funkcia zabezpeci, ze v textovych poliach budu
     * len relevantne znaky (najviac jeden z 1-9, najviac jeden z A-D a najviac jeden z E,O
     * @param x x-ova suradnica policka
     * @param y y-ova suradnica policka
     */
    private void changeGrid(int x, int y) {
        TextField textField = textFields[x][y];

        String irregular = changeIrregular(x, y);
        String extraRegion = changeRegions(x, y);
        String parity = changeParity(x, y);
        String fortress = changeFortress(x, y);

        textField.setText(irregular + extraRegion + parity + fortress);
    }

    /** Funkcia nastavi textovym poliam spravanie pre pripad, ze vrstva je pouzita pri generovani sudoku */
    public void setInputHandlers() {
        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {
                textFields[i][j].setFont(new Font("Arial",20));
                setInputHandler(i, j);
            }
        }
    }

    /** Funkcia nastavi spravanie pri generovani pre konkretne textove pole
     * @param x x-ova suradnica policka
     * @param y y-ova suradnica policka
     */
    private void setInputHandler(int x, int y) {
        textFields[x][y].setOnKeyPressed(event -> {
            TextField up = textFields[(x+8)%9][y];
            TextField down = textFields[(x+1)%9][y];
            TextField left = textFields[x][(y+8)%9];
            TextField right = textFields[x][(y+1)%9];

            /** Umoznenie pohybu sipkami medzi jednotlivymi polickami (cylindricky) */
            if (event.getCode().equals(KeyCode.DOWN)) {
                down.requestFocus();
                updateGrid();
            } else if (event.getCode().equals(KeyCode.UP)) {
                up.requestFocus();
                updateGrid();
            } else if (event.getCode().equals(KeyCode.LEFT)) {
                left.requestFocus();
                updateGrid();
            } else if (event.getCode().equals(KeyCode.RIGHT)) {
                right.requestFocus();
                updateGrid();
            }
        });

        textFields[x][y].setOnMouseClicked(event -> updateGrid());
    }

    /** Funkcia aktualizuje obsah mriezky tak, aby kazde policko obsahovalo maximalne jedno cislo. Pocet zadanych
     * cisel sa vypise v infoBoxe */
    public void updateGrid() {
        this.givens = 0;
        int nulls = 0;
        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {
                filterText(i, j);
                String txt = textFields[i][j].getText();
                if (!txt.equals("") && !txt.equals("0")) {
                    givens++;
                }
                else if (txt.equals("0")) {
                    nulls++;
                }
            }
        }
        if (nulls == 0) {
            this.infoBox.changeGivens(givens.toString());
        }
        else {
            this.infoBox.changeGivens(givens+" ("+(givens+nulls)+")");
        }
    }

    /** Funkcia aktualizuje vrstvu nepravidelnych regionov podla obsahu policka
     * @param x x-ova suradnica policka
     * @param y y-ova suradnica policka
     * @return vrati poradove cislo regionu, ktoremu policko prislucha, alebo "", ak ziadnemu
     */
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

    /** Funkcia aktualizuje vrstvu extra regionov podla obsahu policka
     * @param x x-ova suradnica policka
     * @param y y-ova suradnica policka
     * @return vrati poradove pismeno regionu, ktoremu policko prislucha, alebo "", ak ziadnemu
     */
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

    /** Funkcia aktualizuje paritnu vrstvu podla obsahu policka
     * @param x x-ova suradnica policka
     * @param y y-ova suradnica policka
     * @return vrati E/O, ak je policko parne/neparne, inak vrati ""
     */
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

    /**
     * Funkcia aktualizuje vrstvu predstavujucu pevnost podla obsahu policka
     * @param x x-ova suradnica policka
     * @param y y-ova suradnica policka
     * @return vrati X, ak je policko sucastou pevnosti, inak vrati ""
     */
    private String changeFortress(int x, int y) {
        TextField textField = textFields[x][y];
        String text = textField.getText();
        String fortress = "";

        if (text.contains("X") || text.contains("x")) {
            inputGrid.getFortressLayer().color(x, y);
            fortress = "X";
        } else {
            inputGrid.getFortressLayer().setBlank(x, y);
        }

        return fortress;
    }

    /** Funkcia zabezpeci, ze v policku sa bude nachadzat maximalne jedno z cisel 1-9
     * @param x x-ova suradnica policka
     * @param y y-ova suradnica policka
     */
    private void filterText(int x, int y) {
        TextField text = textFields[x][y];
        String s = "";
        for (Integer i = 0; i <= 9; i++) {
            if (text.getText().contains(i.toString())) {
                s = i.toString();
            }
        }
        text.setText(s);
    }

    /** Funkcia vrati zoznam moznosti v rozsahu 0-8 pre konkretne policko na pozicii x, y
     * @param x x-ova suradnica policka
     * @param y y-ova suradnica policka
     * @return vrati zoznam moznosti cisel pre zadane policko
     */
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

    /**
     * Funkcia vrati zoznam policok, do ktorych pouzivatel vpisal cislo 0 - teda policok urcenych na generovanie
     * @return vrati zoznam policok, do ktorych sa maju dogenerovat cisla
     */
    public List<List<Integer>> getZeros() {
        List<List<Integer>> zeros = new ArrayList<>();
        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {
                TextField text = textFields[i][j];
                if (text.getText().contains("0")) {
                    List<Integer> cell = new ArrayList<>();
                    cell.add(i);
                    cell.add(j);
                    zeros.add(cell);
                }
            }
        }
        return zeros;
    }

    /** Funkcia vrati obsah policka na pozicii x, y
     * @param x x-ova suradnica policka
     * @param y y-ova suradnica policka
     * @return obsah policka na suradniciach x,y
     */
    public String getText(int x, int y) {
        return textFields[x][y].getText();
    }

    /** Funkcia nastavi policku na pozicii x, y prislusny text
     * @param x x-ova suradnica policka
     * @param y y-ova suradnica policka
     * @param text text, ktory sa ma zapisat do policka na suradniciach x,y
     * */
    public void setText(int x, int y, String text) {
        this.textFields[x][y].setText(text);
    }

    /** Funkcia vrati referenciu na zoznam textovych poli
     * @return vrati referenciu na 2D zoznam textovych poli pre kazde policko vo vstupnej mriezke
     */
    public TextField[][] getTextFields() {
        return this.textFields;
    }

    /** Funkcia vrati klon vrstvy s cislami pre vstupnu mriezku
     * @return klon vrstvy s cislami pre vstupnu mriezku
     */
    @Override
    public TextFieldLayer clone() {
        TextFieldLayer cloned = new TextFieldLayer(this.size, this.inputGrid);
        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {
                cloned.getTextFields()[i][j].setText(this.getTextFields()[i][j].getText());
            }
        }
        return cloned;
    }

}
