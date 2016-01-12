package main;

import generators.Generator;
import graphics.grids.InputGrid;
import graphics.grids.OutputGrid;

import java.util.*;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class Sudoku {
    // pre kazdy riadok 0-8, stlpec 0-8 a cislo 0-8 si udrziavame informaciu, ci toto cislo moze byt v policku
    List<List<Set<Integer>>> options;
    List<String> types;
    Generator generator;
    InputGrid inputGrid;
    OutputGrid outputGrid;

    public Sudoku(){
        options = new ArrayList<>();
        for ( int i = 0; i < 9; i++ ) {
            options.add(new ArrayList<>());
        }
        for ( List<Set<Integer>> row : options ) {
            for ( Set cell : row ) {
                for ( int i = 0; i < 9; i++ ) {
                    cell.add(i);
                }
            }
        }
    }

    public void setInputGrid(InputGrid inputGrid) {
        this.inputGrid = inputGrid;
    }

    public void setOutputGrid(OutputGrid outputGrid) {
        this.outputGrid = outputGrid;
    }

    public void setGenerator(Generator generator) {
        this.generator = generator;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }
}
