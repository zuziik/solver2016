package commands.io;

import commands.Command;
import commands.basic.CreateCommand;
import graphics.InfoBox;
import javafx.stage.Stage;
import sudoku.Type;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Trieda reprezentuje prikaz, ktory zabezpeci nacitanie sudoku zo zadaneho suboru
 */
public class ReloadCommand implements Command {

    private final Stage stage;
    private List<List<Integer>> numbers;
    private final Set<Type> types = new HashSet<>();
    private final List<List<Integer>> evens = new ArrayList<>();
    private final List<List<Integer>> odds = new ArrayList<>();
    private final List<List<List<Integer>>> irregulars = new ArrayList<>();
    private final List<List<List<Integer>>> extras = new ArrayList<>();
    private final List<List<Integer>> fortress = new ArrayList<>();
    private final List<List<Integer>> dots = new ArrayList<>();
    private final File selectedFile;
    private final InfoBox infoBox;

    /**
     * @param stage odkaz na aktualne okno
     * @param selectedFile odkaz na subor, z ktoreho sa ma nacitat sudoku
     * @param infoBox odkaz na tabulu, na ktoru sa vypisuju spravy o priebehu programu
     */
    public ReloadCommand(Stage stage, File selectedFile, InfoBox infoBox){
        this.stage = stage;
        this.selectedFile = selectedFile;
        this.infoBox = infoBox;
    }

    /** Funkcia nacita informacie o typoch sudoku a cisel v nom a ulozi ich do samostatnych
     * zoznamov */
    private void createLists(File selectedFile) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(selectedFile));
            this.numbers = new ArrayList<>(9);
            for ( int i = 0; i < 9; i++ ) {
                numbers.add(new ArrayList<>(9));
            }
            for ( int i = 0; i < 9; i++ ) {
                String row = br.readLine();
                for ( int j = 0; j < row.length(); j++ ) {
                    if (row.charAt(j) != '.') {
                        numbers.get(i).add(Character.getNumericValue(row.charAt(j)));
                    }
                    else {
                        numbers.get(i).add(0);
                    }
                }
            }

            String row = br.readLine();
            while ( row != null ) {
                if ( row.equals(Type.Even.toString()) ) {
                    row = br.readLine();
                    this.types.add(Type.Even);
                    String[] arrays = row.substring(1,row.length()-1).split(", ");
                    for ( int i = 0; i < arrays.length; i += 2) {
                        List<Integer> cell = new ArrayList<>();
                        cell.add(Character.getNumericValue(arrays[i].charAt(1)));
                        cell.add(Character.getNumericValue(arrays[i + 1].charAt(0)));
                        evens.add(cell);
                    }
                }
                else if ( row.equals(Type.Odd.toString()) ) {
                    row = br.readLine();
                    this.types.add(Type.Odd);
                    String[] arrays = row.substring(1,row.length()-1).split(", ");
                    for ( int i = 0; i < arrays.length; i += 2) {
                        List<Integer> cell = new ArrayList<>();
                        cell.add(Character.getNumericValue(arrays[i].charAt(1)));
                        cell.add(Character.getNumericValue(arrays[i + 1].charAt(0)));
                        odds.add(cell);
                    }
                }
                else if ( row.equals(Type.Fortress.toString()) ) {
                    row = br.readLine();
                    this.types.add(Type.Fortress);
                    String[] arrays = row.substring(1,row.length()-1).split(", ");
                    for ( int i = 0; i < arrays.length; i += 2) {
                        List<Integer> cell = new ArrayList<>();
                        cell.add(Character.getNumericValue(arrays[i].charAt(1)));
                        cell.add(Character.getNumericValue(arrays[i + 1].charAt(0)));
                        fortress.add(cell);
                    }
                }
                else if ( row.equals(Type.Irregular.toString()) ) {
                    row = br.readLine();
                    this.types.add(Type.Irregular);
                    String r = row.substring(1,row.length()-1);
                    int diff = 0;
                    List<Integer> cell = new ArrayList<>();
                    List<List<Integer>> region = new ArrayList<>();
                    for ( int i = 0; i < r.length(); i++ ) {
                        char c = r.charAt(i);
                        switch (c) {
                            case ' ': break;
                            case ',': break;
                            case '[': {
                                diff++;
                                if ( diff == 1 ) {
                                    region = new ArrayList<>();
                                }
                                else {
                                    cell = new ArrayList<>();
                                }
                                break;
                            }
                            case ']': {
                                diff--;
                                if ( diff == 0 ) {
                                    this.irregulars.add(region);
                                }
                                else {
                                    region.add(cell);
                                }
                                break;
                            }
                            default: cell.add(Character.getNumericValue(c));
                        }
                    }
                }
                else if ( row.equals(Type.ExtraRegion.toString()) ) {
                    row = br.readLine();
                    this.types.add(Type.ExtraRegion);
                    String r = row.substring(1,row.length()-1);
                    int diff = 0;
                    List<Integer> cell = new ArrayList<>();
                    List<List<Integer>> region = new ArrayList<>();
                    for ( int i = 0; i < r.length(); i++ ) {
                        char c = r.charAt(i);
                        switch (c) {
                            case ' ': break;
                            case ',': break;
                            case '[': {
                                diff++;
                                if ( diff == 1 ) {
                                    region = new ArrayList<>();
                                }
                                else {
                                    cell = new ArrayList<>();
                                }
                                break;
                            }
                            case ']': {
                                diff--;
                                if ( diff == 0 ) {
                                    this.extras.add(region);
                                }
                                else {
                                    region.add(cell);
                                }
                                break;
                            }
                            default: cell.add(Character.getNumericValue(c));
                        }
                    }
                }
                else if ( row.equals(Type.Consecutive.toString()) ) {
                    row = br.readLine();
                    this.types.add(Type.Consecutive);
                    String[] arrays = row.substring(1,row.length()-1).split(", ");
                    for ( int i = 0; i < arrays.length; i += 4) {
                        List<Integer> dot = new ArrayList<>();
                        dot.add(Character.getNumericValue(arrays[i].charAt(1)));
                        dot.add(Character.getNumericValue(arrays[i+1].charAt(0)));
                        dot.add(Character.getNumericValue(arrays[i+2].charAt(0)));
                        dot.add(Character.getNumericValue(arrays[i+3].charAt(0)));
                        dots.add(dot);
                    }
                }
                else if ( row.equals(Type.Classic.toString()) ) {
                    this.types.add(Type.Classic);
                }
                else if ( row.equals(Type.Diagonal.toString()) ) {
                    this.types.add(Type.Diagonal);
                }
                else if ( row.equals(Type.Antiknight.toString())) {
                    this.types.add(Type.Antiknight);
                }
                else if ( row.equals(Type.DisjointGroups.toString()) ) {
                    this.types.add(Type.DisjointGroups);
                }
                else if ( row.equals(Type.NonConsecutive.toString()) ) {
                    this.types.add(Type.NonConsecutive);
                }
                else if ( row.equals(Type.Untouchable.toString()) ) {
                    this.types.add(Type.Untouchable);
                }
                row = br.readLine();
            }
            createSudoku();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error occured while loading sudoku from file: File in a wrong format!");
        }
    }

    /** Funkcia vytvori nove sudoku na zaklade informacii precitanych zo suboru */
    private void createSudoku() {
        Command command = new CreateCommand(numbers, stage, types, irregulars, extras, fortress, evens, odds, dots, selectedFile);
        command.execute();
        if (this.infoBox != null) {
            this.infoBox.addInfo("Sudoku loaded");
        }
    }

    /**
     * Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou
     */
    @Override
    public void execute() {
        createLists(selectedFile);
    }
}
