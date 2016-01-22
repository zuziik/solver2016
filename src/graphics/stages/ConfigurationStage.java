package graphics.stages;

import commands.Command;
import commands.CreateCommand;
import graphics.Style;
import graphics.grids.InputGrid;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sudoku.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Trieda reprezentuje okno na nastavenie typov sudoku
 */
public class ConfigurationStage {
    private final BorderPane pane = new BorderPane();
    private final Scene scene = new Scene(pane);
    private final Stage stage = new Stage();
    private final InputGrid inputGrid;
    private final VBox functionBox = new VBox();
    private RadioButton b1_classic;
    private RadioButton b2_diagonal;
    private RadioButton b3_untouchable;
    private RadioButton b4_nonconsecutive;
    private RadioButton b5_disjoint;
    private RadioButton b6_antiknight;
    private List<RadioButton> rButtons;
    private final Button create;
    private final Button restart;
    private final Label info;
    private final Button help;
    private List<List<List<Integer>>> irregulars = new ArrayList<>();
    private List<List<List<Integer>>> extras = new ArrayList<>();
    private List<List<Integer>> odds = new ArrayList<>();
    private List<List<Integer>> evens = new ArrayList<>();
    private Set<Type> types;
    private final int width = 250;

    public ConfigurationStage() {
        stage.setScene(scene);
        stage.setTitle("Settings");
        stage.setResizable(false);
        inputGrid = new InputGrid();
        pane.setLeft(inputGrid);
        createRadioButtons();
        this.restart = new Button("Restart");
        this.create = new Button("Create");
        addCreateHandlers();
        Style.setButtonStyle(restart, width);
        addRestartHandlers();
        Style.setButtonStyle(create, width);
        this.info = new Label();
        setInfo();
        this.help = new Button("Show Info");
        Style.setHelpButtonStyle(help, info);
        functionBox.getChildren().addAll(restart, create, help);
        functionBox.getChildren().addAll(rButtons);
        functionBox.getChildren().add(info);
        pane.setRight(functionBox);
        scene.setOnMouseClicked(event -> inputGrid.getTextFieldLayer().changeGrid());
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                create.fire();
            }
        });
    }

    /** Funkcia nastavi obsah informacneho textu */
    private void setInfo() {
        this.info.setText("IRREGULAR\n" +
                "Mark cells with numbers 1-9 to create irregular regions. " +
                "Same numbers belong to the same region. " +
                "Regions 1-9 may not overlap each other.\n" +
                "EXTRA REGIONS\n" +
                "Mark cells with letters A-D to create extra regions. " +
                "Same letters belong to the same region. " +
                "Regions A-D may not overlap each other but they can overlap regions 1-9.\n" +
                "EVEN/ODD\n" +
                "Write E/O into a cell to mark it EVEN/ODD.");
        this.info.setBackground(new Background(new BackgroundFill(Color.color(0.9062, 0.9297, 0.9453), null, null)));
        this.info.setWrapText(true);
        this.info.setOpacity(0);
        this.info.setPrefWidth(250);
    }

    /** Funkcia sa postara o vytvorenie radio buttonov na vyber typov sudoku */
    private void createRadioButtons() {
        rButtons = new ArrayList<>();

        b1_classic = new RadioButton("Classic");
        b1_classic.setSelected(true);
        b2_diagonal = new RadioButton("Diagonal");
        b3_untouchable = new RadioButton("Untouchable");
        b4_nonconsecutive = new RadioButton("Nonconsecutive");
        b5_disjoint = new RadioButton("Disjoint Groups");
        b6_antiknight = new RadioButton("Antiknight");
        rButtons.add(b1_classic);
        rButtons.add(b2_diagonal);
        rButtons.add(b3_untouchable);
        rButtons.add(b4_nonconsecutive);
        rButtons.add(b5_disjoint);
        rButtons.add(b6_antiknight);
        for (RadioButton radioButton : rButtons) {
            Style.setRadioButtonStyle(radioButton);
        }

        setUpdateActions(b1_classic);
        setUpdateActions(b2_diagonal);
        setUpdateActions(b3_untouchable);
        setUpdateActions(b4_nonconsecutive);
        setUpdateActions(b5_disjoint);
        setUpdateActions(b6_antiknight);

        b1_classic.setOnAction(event -> {
            if (b1_classic.isSelected()) {
                inputGrid.getBorderLayer().showBorders();
            } else {
                inputGrid.getBorderLayer().hideBorders();
            }
        });

        b2_diagonal.setOnAction(event -> {
            if (b2_diagonal.isSelected()) {
                inputGrid.getDiagonalLayer().showDiagonals();
            } else {
                inputGrid.getDiagonalLayer().hideDiagonals();
            }
        });

    }

    /** Funkcia zabezpeci spravanie tlacidlu btn */
    private void setUpdateActions(RadioButton b) {
        b.setOnMouseClicked(event -> inputGrid.getTextFieldLayer().changeGrid());
    }

    /** Funkcia vrati region s viac ako 9 polickami alebo "", ak ziaden taky neexistuje */
    private String findWrongRegion() {
        Integer i = 1;
        for ( List<List<Integer>> region : this.irregulars ) {
            if ( region.size() > 9 ) {
                return i.toString();
            }
            i++;
        }

        Character c = 'A';
        for ( List<List<Integer>> region : this.extras ) {
            if ( region.size() > 9 ) {
                return c.toString();
            }
            c++;
        }

        return "";
    }

    /** Funkcia nastavi spravanie tlacidlu create - skontroluje, ci maju regiony spravny pocet policok a vytvori
     * sudoku zodpovedajuce vybranym typom */
    private void addCreateHandlers() {
        create.setOnAction(event -> {
            inputGrid.getTextFieldLayer().changeGrid();
            updateLists();
            String wrongRegion = findWrongRegion();
            if (wrongRegion.equals("")) {
                Command command = new CreateCommand(null, stage, types, irregulars, extras, evens, odds, null);
                command.execute();
            }
            else {
                System.out.println("This region is wrong: "+wrongRegion);
            }
        });
    }

    /** Funkcia nastavi spravanie tlacidlu restart - obnovi predvolene nastavenia sudoku */
    private void addRestartHandlers() {
        restart.setOnAction(event -> {
            ConfigurationStage configurationStage = new ConfigurationStage();
            configurationStage.show();
            stage.close();
        });
    }

    /** Funkcia vytvori zoznam typov sudoku a zoznam vsetkych extra regionov a parnych/neparnych policok */
    private void updateLists() {
        this.types = new HashSet<>();

        if (b1_classic.isSelected()) {
            types.add(Type.Classic);
        }
        if (b2_diagonal.isSelected()) {
            types.add(Type.Diagonal);
        }
        if (b3_untouchable.isSelected()) {
            types.add(Type.Untouchable);
        }
        if (b4_nonconsecutive.isSelected()) {
            types.add(Type.NonConsecutive);
        }
        if (b5_disjoint.isSelected()) {
            types.add(Type.DisjointGroups);
        }
        if (b6_antiknight.isSelected()) {
            types.add(Type.Antiknight);
        }

        this.evens.clear();
        List<List<Integer>> evens1 = inputGrid.getEven();
        if (evens1.size() > 0) {
            types.add(Type.Even);
            this.evens.addAll(evens1);
        }

        this.odds.clear();
        List<List<Integer>> odds1 = inputGrid.getOdd();
        if (odds1.size() > 0) {
            types.add(Type.Odd);
            this.odds.addAll(odds1);
        }

        this.irregulars.clear();
        List<List<List<Integer>>> irregulars1 = inputGrid.getIrregulars();
        if (irregulars1.size() > 0) {
            types.add(Type.Irregular);
            this.irregulars.addAll(irregulars1);
        }

        this.extras.clear();
        List<List<List<Integer>>> extras1 = inputGrid.getExtras();
        if (extras1.size() > 0) {
            types.add(Type.ExtraRegion);
            this.extras.addAll(extras1);
        }

    }

    /** Funkcia zabezpeci zobrazenie okna s nastaveniami typov */
    public void show() {
        stage.show();
    }
}
