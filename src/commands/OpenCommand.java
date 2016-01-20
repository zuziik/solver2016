package commands;

import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sudoku.Sudoku;
import sudoku.Type;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class OpenCommand implements Command {

    Stage stage;
    List<List<Integer>> numbers;
    Set<Type> types = new HashSet<>();
    List<List<Integer>> evens = new ArrayList<>();
    List<List<Integer>> odds = new ArrayList<>();
    List<List<List<Integer>>> irregulars = new ArrayList<>();
    List<List<List<Integer>>> extras = new ArrayList<>();

    public OpenCommand(Stage stage){
        this.stage = stage;
    }

    private void createLists(File selectedFile) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(selectedFile));
            Sudoku sudoku = new Sudoku();
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
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error occured while loading sudoku from file: File in a wrong format!");
        }
    }

    private void createSudoku() {
        Command command = new CreateCommand(numbers, stage, types, irregulars, extras, evens, odds);
        command.execute();
    }

    /**
     * Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou
    */
    @Override
    public void execute() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("files/sudoku"));
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(
                new ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            createLists(selectedFile);
        }
    }
}
