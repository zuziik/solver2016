package sudoku;

import generators.Generator;
import graphics.InfoBox;
import graphics.grids.InputGrid;
import graphics.grids.OutputGrid;

import java.io.File;
import java.util.*;

/**
 * Trieda reprezentuje SUDOKU a obsahuje informacie na vsetky ostatne relevantne triedy
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

    /** Funkcia nastavi sudoku referenciu na informacny panel */
    public void setInfoBox(InfoBox infoBox) {
        this.infoBox = infoBox;
        this.inputGrid.getTextFieldLayer().setInfoBox(infoBox);
    }

    /** Funkcia vrati referenciu na zoznam moznosti pre kazde policko v sudoku */
    public List<List<Set<Integer>>> getOptions() {
        return this.options;
    }

    /** Funkcia nastavi sudoku referenciu na vstupnu mriezku */
    public void setInputGrid(InputGrid inputGrid) {
        this.inputGrid = inputGrid;
    }

    /** Funkcia vrati referenciu na vstupnu mriezku */
    public InputGrid getInputGrid() {
        return this.inputGrid;
    }

    /** Funkcia nastavi sudoku referenciu na vystupnu mriezku */
    public void setOutputGrid(OutputGrid outputGrid) {
        this.outputGrid = outputGrid;
    }

    /** Funkcia vrati referenciu na vystupnu mriezku */
    public OutputGrid getOutputGrid() {
        return this.outputGrid;
    }

    /** Funkcia nastavi sudoku referenciu na relevantny generator */
    public void setGenerator(Generator generator) {
        this.generator = generator;
    }

    /** Funkcia vrati referenciu na generator tohto sudoku */
    public Generator getGenerator() {
        return this.generator;
    }

    /** Funkcia vrati predvoleny subor konkretneho sudoku */
    public File getFile() {
        return this.file;
    }

    /** Funkcia nastavi subor, do ktoreho sa ma sudoku ukladat a z ktoreho sa ma nacitavat */
    public void setFile(File file) {
        if (file != null) {
            this.file = file;
        }
    }

    /** Funkcia nastavi sudoku zoznam jeho typov */
    public void setTypes(Set<Type> types) {
        this.types = types;
    }

    /** Funkcia vrati zoznam typov sudoku */
    public Set<Type> getTypes() {
        return this.types;
    }

    /** Funkcia vrati zoznam parnych policok */
    public List<List<Integer>> getEvens() {
        return this.evens;
    }

    /** Funkcia nastavi sudoku zoznam parnych policok */
    public void setEvens(List<List<Integer>> evens) {
        this.evens = evens;
    }

    /** Funkcia vrati zoznam neparnych policok */
    public List<List<Integer>> getOdds() {
        return this.odds;
    }

    /** Funkcia nastavi sudoku zoznam parnych policok */
    public void setOdds(List<List<Integer>> odds) {
        this.odds = odds;
    }

    /** Funkcia vrati zoznam nepravidelnych regionov */
    public List<List<List<Integer>>> getIrregulars() {
        return this.irregulars;
    }

    /** Funkcia nastavi sudoku zoznam nepravidelnych regionov */
    public void setIrregulars(List<List<List<Integer>>> irregulars) {
        this.irregulars = irregulars;
    }

    /** Funkcia vrati zoznam extra regionov */
    public List<List<List<Integer>>> getExtras() {
        return this.extras;
    }

    /** Funkcia nastavi sudoku zoznam extra regionov */
    public void setExtras(List<List<List<Integer>>> extras) {
        this.extras = extras;
    }

    /** Funkcia nastavi sudoku moznosti pre cisla podla zoznamu */
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
