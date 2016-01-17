package sudoku;

import com.sun.javafx.scene.control.skin.IntegerFieldSkin;
import generators.Generator;
import graphics.grids.InputGrid;
import graphics.grids.OutputGrid;

import java.io.File;
import java.util.*;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class Sudoku {
    // pre kazdy riadok 0-8, stlpec 0-8 a cislo 0-8 si udrziavame informaciu, ci toto cislo moze byt v policku
    private List<List<Set<Integer>>> options;
    private Set<Type> types;
    private Generator generator;
    private InputGrid inputGrid;
    private OutputGrid outputGrid;
    private File file;
    private List<List<List<Integer>>> regions;
    private List<List<Integer>> odds;
    private List<List<Integer>> evens;

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
            for ( Set cell : row ) {
                for ( int i = 0; i < 9; i++ ) {
                    cell.add(i);
                }
            }
        }
        this.file = new File("files/sudoku/default.txt");
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
        this.file = file;
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

    public List<List<List<Integer>>> getRegions() {
        return this.regions;
    }

    public void setRegions(List<List<List<Integer>>> regions) {
        this.regions = regions;
    }


}