package main;

import com.sun.javafx.scene.control.skin.IntegerFieldSkin;
import generators.Generator;
import graphics.grids.InputGrid;
import graphics.grids.OutputGrid;

import java.util.*;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class Sudoku {
    // pre kazdy riadok 0-8, stlpec 0-8 a cislo 0-8 si udrziavame informaciu, ci toto cislo moze byt v policku
    private List<List<Set<Integer>>> options;
    private List<String> types;
    private Generator generator;
    private InputGrid inputGrid;
    private OutputGrid outputGrid;

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

    public void setTypes(List<String> types) {
        this.types = types;
    }



}
