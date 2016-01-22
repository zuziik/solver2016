package sudoku;

import generators.Generator;
import graphics.InfoBox;
import graphics.grids.InputGrid;
import graphics.grids.OutputGrid;

import java.io.File;
import java.util.*;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class Sudoku {
    // pre kazdy riadok 0-8, stlpec 0-8 a cislo 0-8 si udrziavame informaciu, ci toto cislo moze byt v policku
    private final List<List<Set<Integer>>> options;
    private Set<Type> types;
    private Generator generator;
    private InputGrid inputGrid;
    private OutputGrid outputGrid;
    private File file;
    private List<List<List<Integer>>> irregulars;
    private List<List<List<Integer>>> extras;
    private List<List<Integer>> odds;
    private List<List<Integer>> evens;
    private InfoBox infoBox;

    public Sudoku(){
        options = new ArrayList<>();
        for ( int i = 0; i < 9; i++ ) {
            options.add(new ArrayList<>());
        }
        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {
                options.get(i).add(new TreeSet<>());
            }
        }
        for ( List<Set<Integer>> row : options ) {
            for ( Set<Integer> cell : row ) {
                for ( int i = 0; i < 9; i++ ) {
                    cell.add(i);
                }
            }
        }
        this.file = new File("files/sudoku/default.txt");
    }

    public void setInfoBox(InfoBox infoBox) {
        this.infoBox = infoBox;
        this.inputGrid.getTextFieldLayer().setInfoBox(infoBox);
    }

    public List<List<Set<Integer>>> getOptions() {
        return this.options;
    }

    public void setInputGrid(InputGrid inputGrid) {
        this.inputGrid = inputGrid;
    }

    public InputGrid getInputGrid() {
        return this.inputGrid;
    }

    public void setOutputGrid(OutputGrid outputGrid) {
        this.outputGrid = outputGrid;
    }

    public OutputGrid getOutputGrid() {
        return this.outputGrid;
    }

    public void setGenerator(Generator generator) {
        this.generator = generator;
    }

    public Generator getGenerator() {
        return this.generator;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        if (file != null) {
            this.file = file;
        }
    }

    public void setTypes(Set<Type> types) {
        this.types = types;
    }

    public Set<Type> getTypes() {
        return this.types;
    }

    public List<List<Integer>> getEvens() {
        return this.evens;
    }

    public void setEvens(List<List<Integer>> evens) {
        this.evens = evens;
    }

    public List<List<Integer>> getOdds() {
        return this.odds;
    }

    public void setOdds(List<List<Integer>> odds) {
        this.odds = odds;
    }

    public List<List<List<Integer>>> getIrregulars() {
        return this.irregulars;
    }

    public void setIrregulars(List<List<List<Integer>>> irregulars) {
        this.irregulars = irregulars;
    }

    public List<List<List<Integer>>> getExtras() {
        return this.extras;
    }

    public void setExtras(List<List<List<Integer>>> extras) {
        this.extras = extras;
    }

    public void setNumbers(List<List<Integer>> numbers) {
        int i = 0;
        for (List<Integer> row : numbers) {
            int j = 0;
            for (Integer num : row) {
                String text = "";
                if (num != 0) {
                    text = num.toString();
                }
                this.inputGrid.getTextFieldLayer().setText(i,j,text);
                j++;
            }
            i++;
        }
    }
}
