package main;

import generators.Generator;
import graphics.grids.InputGrid;
import graphics.grids.OutputGrid;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class Sudoku {
    // pre kazdy riadok 0-8, stlpec 0-8 a cislo 0-8 si udrziavame informaciu, ci toto cislo moze byt v policku
    ArrayList<ArrayList<HashMap<Integer,Boolean>>> options;
    ArrayList<String> types;
    Generator generator;
    InputGrid inputGrid;
    OutputGrid outputGrid;

    public void setInputGrid(InputGrid inputGrid) {
        this.inputGrid = inputGrid;
    }

    public void setOutputGrid(OutputGrid outputGrid) {
        this.outputGrid = outputGrid;
    }

    public void setGenerator(Generator generator) {
        this.generator = generator;
    }
}
