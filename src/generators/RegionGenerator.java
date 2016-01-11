package generators;

import java.util.ArrayList;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class RegionGenerator extends SuperGenerator {

    //zoznam regionov (teda zoznamov policok - dvojic suradnic)
    ArrayList<ArrayList<ArrayList<Integer>>> regions;

    public RegionGenerator(Generator wrapped, ArrayList<ArrayList<ArrayList<Integer>>> regions) {
        super(wrapped);
        this.regions = regions;
    }

    private void generateRegions() {
        //TODO
    }

    /**
     * Funkcia vygeneruje CNF podla typu sudoku - generatora a prida ich do zoznamu formulas
     */
    @Override
    public void generateCNF() {
        super.generateCNF();
        generateRegions();
    }
}
