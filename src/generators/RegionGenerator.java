package generators;

import java.util.List;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class RegionGenerator extends SuperGenerator {

    //zoznam regionov (teda zoznamov policok - dvojic suradnic)
    private final List<List<List<Integer>>> regions;

    public RegionGenerator(Generator wrapped, List<List<List<Integer>>> regions) {
        super(wrapped);
        this.regions = regions;
    }

    private void generateRegions() {
        for ( List<List<Integer>> region : regions ) {
            super.generateRegion(region);
        }
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
